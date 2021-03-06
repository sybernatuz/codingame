package utils;

import objects.Card;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortUtils {

    public static List<Card> sortByCost(List<Card> cards) {
        return cards.stream()
                .sorted(Comparator.comparing(Card::getCost))
                .collect(Collectors.toList());
    }

    public static List<Card> sortByAttack(List<Card> cards) {
        return cards.stream()
                .sorted(Comparator.comparing(Card::getAttack))
                .collect(Collectors.toList());
    }
}
