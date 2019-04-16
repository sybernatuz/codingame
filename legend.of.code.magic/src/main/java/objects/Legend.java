package objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Legend {

    public List<Card> deck = new ArrayList<>();
    public int        playerHealth;
    public int        playerMana;
    public int        playerDeck;
    public int        playerRune;
    public int        playerDraw;

    public void init(Scanner in) {
        this.playerHealth = in.nextInt();
        this.playerMana = in.nextInt();
        this.playerDeck = in.nextInt();
        this.playerRune = in.nextInt();
        this.playerDraw = in.nextInt();
    }

    public List<Card> getCardsPlayable() {
        List<Card> cardsUnderManaCost;
        cardsUnderManaCost =  this.deck.stream()
                .filter(cardDeck -> cardDeck.cost <= this.playerMana)
                .collect(Collectors.toList());
        return cardsUnderManaCost;
    }
}
