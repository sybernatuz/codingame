package objects;

import java.util.ArrayList;
import java.util.List;

public class Attack {

    public Card targetCard;
    public List<Card> attackerCards;

    public Attack() {
        attackerCards = new ArrayList<>();
    }

    public Attack(Card attacker, Card target) {
        attackerCards = new ArrayList<>();
        this.attackerCards.add(attacker);
        this.targetCard = target;
    }
}
