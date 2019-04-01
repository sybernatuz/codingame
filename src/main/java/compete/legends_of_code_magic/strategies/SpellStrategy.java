package main.java.compete.legends_of_code_magic.strategies;

import main.java.compete.legends_of_code_magic.enums.AbilitiesEnum;
import main.java.compete.legends_of_code_magic.enums.CardTypeEnum;
import main.java.compete.legends_of_code_magic.managers.UseManager;
import main.java.compete.legends_of_code_magic.objects.*;
import main.java.compete.legends_of_code_magic.utils.FindUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SpellStrategy {

    private UseManager useUtils = new UseManager();

    public List<UseSpell> computeSpellsToUse(List<Card> cardsPlayable, Legend me, Board board) {
        boolean isSpellOnDeck = cardsPlayable.stream()
                .anyMatch(card -> !card.cardType.equals(CardTypeEnum.CREATURE));
        if (!isSpellOnDeck)
            return Collections.emptyList();

        Spells blueSpells = new Spells(CardTypeEnum.BLUE_ITEM, cardsPlayable);
        Spells redSpells = new Spells(CardTypeEnum.RED_ITEM, cardsPlayable);
        Spells greenSpells = new Spells(CardTypeEnum.GREEN_ITEM, cardsPlayable);

        List<UseSpell> possibleSpellsToUse = new ArrayList<>();

        if (blueSpells.cards.size() > 0)
            possibleSpellsToUse.addAll(useBlueSpellStrategy(blueSpells, me));

        if (redSpells.cards.size() > 0)
            possibleSpellsToUse.addAll(useRedSpellsStrategy(redSpells, board));

        if (greenSpells.cards.size() > 0)
            possibleSpellsToUse.addAll(useGreenSpellStrategy(greenSpells, board, me));

        List<UseSpell> spellsToUse = new ArrayList<>();
        possibleSpellsToUse.forEach(spell -> useUtils.addSpell(spell, spellsToUse, me, board));
        return spellsToUse;
    }

    private List<UseSpell> useBlueSpellStrategy(Spells blueSpells, Legend me) {
        return Collections.emptyList();
    }

    private List<UseSpell> useRedSpellsStrategy(Spells redSpells, Board board) {
        if (board.opponentCards.size() == 0)
            return Collections.emptyList();
        Card firstBoardCard = board.opponentCards.get(0);
        return redSpells.cards.stream()
                .map(spell -> new UseSpell(spell, firstBoardCard))
                .collect(Collectors.toList());
    }

    private List<UseSpell> useGreenSpellStrategy(Spells greenSpells, Board board, Legend me) {
        if (!isGreenSpellPriority(board, me))
            return Collections.emptyList();

        Card HighestCostBoardCard = board.myCards.stream()
                .max(Comparator.comparing(Card::getCost))
                .orElse(board.myCards.get(0));
        return greenSpells.cards.stream()
                .map(spell -> new UseSpell(spell, HighestCostBoardCard))
                .collect(Collectors.toList());
    }

    private boolean isGreenSpellPriority(Board board, Legend me) {
        boolean isCardOnBoard = board.myCards.size() > 0;
        boolean isGuardOnBoard = FindUtils.findByAbility(board.myCards, AbilitiesEnum.GUARD).size() > 0;
        boolean isPossibleGuardToSummon = false;
        if (!isGuardOnBoard)
            isPossibleGuardToSummon = FindUtils.findByAbility(me.getCardsPlayable(), AbilitiesEnum.GUARD).size() > 0;
        return isCardOnBoard && !isPossibleGuardToSummon;
    }
}
