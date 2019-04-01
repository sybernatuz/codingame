package main.java.compete.legends_of_code_magic.objects;

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
