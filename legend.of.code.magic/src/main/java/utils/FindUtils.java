package utils;

import enums.AbilitiesEnum;
import enums.CardTypeEnum;
import objects.Card;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FindUtils {

    public static List<Card> findByType(List<Card> cards, CardTypeEnum cardType) {
        return cards.stream()
                .filter(card -> card.cardType.equals(cardType))
                .collect(Collectors.toList());
    }

    public static List<Card> findByTypeAndAbility(List<Card> cards, CardTypeEnum cardType, AbilitiesEnum abilities) {
        return findByAbility(findByType(cards, cardType), abilities);
    }

    public static List<Card> findByAbility(List<Card> cards, AbilitiesEnum abilities) {
        return cards.stream()
                .filter(card -> card.abilities.contains(abilities))
                .collect(Collectors.toList());
    }

    public static List<Card> findByAttackAvailable(List<Card> cards) {
        return cards.stream()
                .filter(Card::isAttackAvailable)
                .collect(Collectors.toList());
    }

    public static Card findOptimalAttackerWithPossibleDeath(Card opponentCard, List<Card> myBoardCards) {
        return findOptimalSolution(opponentCard, myBoardCards)
                .orElse(myBoardCards.stream()
                        .filter(card -> card.attack >= opponentCard.defense && card.cost < opponentCard.cost && card.isAttackAvailable)
                        .findFirst()
                        .orElse(null)
                );
    }

    public static Card findCardInList(Card card, List<Card> cards) {
        return cards.get(cards.indexOf(card));
    }

    public static Integer findPositionById(int id, List<Card> cards) {
        Card cardById = cards.stream()
                .filter(card -> card.instanceId == id)
                .findFirst()
                .orElse(null);
        if (cardById == null)
            return null;
        return cards.indexOf(cardById);
    }

    public static Card findOptimalAttackerWithoutDeath(Card opponentCard, List<Card> myBoardCards) {
        return findOptimalSolution(opponentCard, myBoardCards).orElse(null);
    }

    private static Optional<Card> findOptimalSolution(Card opponentCard, List<Card> myBoardCards) {
        return myBoardCards.stream()
                .filter(card -> card.attack >= opponentCard.defense)
                .filter(card -> card.defense > opponentCard.attack)
                .findFirst();
    }
}
