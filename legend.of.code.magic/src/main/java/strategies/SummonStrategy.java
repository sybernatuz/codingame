package strategies;

import enums.AbilitiesEnum;
import enums.CardTypeEnum;
import managers.UseManager;
import objects.Board;
import objects.Card;
import objects.Legend;
import utils.FindUtils;

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
