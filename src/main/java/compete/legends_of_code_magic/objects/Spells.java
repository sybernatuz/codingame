package main.java.compete.legends_of_code_magic.objects;

import main.java.compete.legends_of_code_magic.enums.CardTypeEnum;
import main.java.compete.legends_of_code_magic.utils.FindUtils;

import java.util.List;

public class Spells {

    public CardTypeEnum cardType;
    public List<Card> cards;

    public Spells(CardTypeEnum cardType, List<Card> cards) {
        this.cardType = cardType;
        this.cards = FindUtils.findByType(cards, cardType);
    }
}
