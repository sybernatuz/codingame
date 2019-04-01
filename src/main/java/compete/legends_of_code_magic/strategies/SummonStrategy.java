package main.java.compete.legends_of_code_magic.strategies;

import main.java.compete.legends_of_code_magic.enums.AbilitiesEnum;
import main.java.compete.legends_of_code_magic.enums.CardTypeEnum;
import main.java.compete.legends_of_code_magic.managers.UseManager;
import main.java.compete.legends_of_code_magic.objects.Board;
import main.java.compete.legends_of_code_magic.objects.Card;
import main.java.compete.legends_of_code_magic.objects.Legend;
import main.java.compete.legends_of_code_magic.utils.FindUtils;

import java.util.ArrayList;
import java.util.List;

public class SummonStrategy {

    private UseManager useManager = new UseManager();

    public List<Card> computeCardsToSummon(List<Card> cardsPlayable, Legend me, Board board) {
        List<Card> cardsToSummon = new ArrayList<>();
        List<Card> possibleGuardCardsToSummon = FindUtils.findByTypeAndAbility(cardsPlayable, CardTypeEnum.CREATURE, AbilitiesEnum.GUARD);
        if (possibleGuardCardsToSummon.size() > 0)
            possibleGuardCardsToSummon.forEach(card -> useManager.addCard(card, cardsToSummon, me, board));

        cardsPlayable = me.getCardsPlayable();
        List<Card> possibleCardsToSummon = FindUtils.findByType(cardsPlayable, CardTypeEnum.CREATURE);
        if (possibleCardsToSummon.size() > 0)
            possibleCardsToSummon.forEach(card -> useManager.addCard(card, cardsToSummon, me, board));

        return cardsToSummon;
    }
}
