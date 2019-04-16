package managers;

import enums.AbilitiesEnum;
import enums.CardTypeEnum;
import objects.Card;
import objects.Legend;
import utils.FindUtils;
import utils.LoggerUtils;
import utils.SortUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ChoseCardManager {

    private static final int MAX_GREEN_SPELLS_NUMBER = 5;

    public String choseDraftCard(List<Card> possibleCards, Legend me) {
        LoggerUtils.title("Chose a card");
        Card chosenCard = getChosenCard(possibleCards, me);
        LoggerUtils.card("Chosen card", chosenCard);
        me.deck.add(chosenCard);
        return "PICK " + possibleCards.indexOf(chosenCard);
    }

    private Card getChosenCard(List<Card> possibleCards, Legend me) {
        List<Card> greenSpellCards = FindUtils.findByType(possibleCards, CardTypeEnum.GREEN_ITEM);

        if (isGreenCardToTake(me, greenSpellCards, possibleCards))
            return greenSpellCards.stream()
                    .filter(card -> card.attack > 0 || card.defense > 0)
                    .findFirst()
                    .orElse(possibleCards.get(0));

        List<Card> creatureCards = FindUtils.findByType(possibleCards, CardTypeEnum.CREATURE);
        creatureCards = creatureCards.stream()
                .filter(card -> card.attack > 0)
                .collect(Collectors.toList());

        if (creatureCards.size() == 0)
            return possibleCards.get(0);

        List<Card> guardCreaturesCards = SortUtils.sortByCost(FindUtils.findByAbility(creatureCards, AbilitiesEnum.GUARD));
        return guardCreaturesCards.stream()
                .findFirst()
                .orElse(creatureCards.get(0));
    }

    private boolean isGreenCardToTake(Legend me, List<Card> greenSpellCards, List<Card> possibleCards) {
        List<Card> myGreenSpellCards = FindUtils.findByType(me.deck, CardTypeEnum.GREEN_ITEM);
        boolean isGreenSpellOnProposedCards = greenSpellCards.size() > 0;
        boolean isMaxGreenSpellsSize = myGreenSpellCards.size() > MAX_GREEN_SPELLS_NUMBER;
        boolean isOnlySpellsOnProposedCards = FindUtils.findByType(possibleCards, CardTypeEnum.CREATURE).size() == 0;
        boolean isOnlyAbilityItems = possibleCards.stream().allMatch(card -> card.attack == 0 && card.defense == 0);
        return isGreenSpellOnProposedCards && !isOnlyAbilityItems && (!isMaxGreenSpellsSize || isOnlySpellsOnProposedCards);
    }
}
