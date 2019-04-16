package objects;

import enums.CardTypeEnum;
import utils.FindUtils;

import java.util.List;

public class Spells {

    public CardTypeEnum cardType;
    public List<Card>   cards;

    public Spells(CardTypeEnum cardType, List<Card> cards) {
        this.cardType = cardType;
        this.cards = FindUtils.findByType(cards, cardType);
    }
}
