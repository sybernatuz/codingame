package objects;

import java.util.ArrayList;
import java.util.List;

public class Board {

    public List<Card> myCards;
    public List<Card> opponentCards;

    public Board() {
        this.myCards = new ArrayList<>();
        this.opponentCards = new ArrayList<>();
    }
}
