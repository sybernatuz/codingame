import managers.ActionManager;
import managers.ChoseCardManager;
import objects.Board;
import objects.Card;
import objects.Legend;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {

    private static ActionManager actionManager = new ActionManager();
    private static ChoseCardManager choseCardManager = new ChoseCardManager();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Legend me = new Legend();
        Legend opponent = new Legend();
        Board board = new Board();
        boolean isDraftOver = false;
        // game loop
        while (true) {
            initPlayers(in, me, opponent);
            int opponentHand = in.nextInt();
            int opponentActions = in.nextInt();
            if (in.hasNextLine()) {
                in.nextLine();
            }
            for (int i = 0; i < opponentActions; i++) {
                String cardNumberAndAction = in.nextLine();
            }
            int cardCount = in.nextInt();
            if (cardCount != 3 && !isDraftOver)
                isDraftOver = true;
            if (!isDraftOver)
                draftPhase(in, cardCount, me);
            if (isDraftOver)
                battlePhase(in, board, me, cardCount);
        }
    }
    private static void draftPhase(Scanner in, int cardCount, Legend me) {
        List<Card> possibleCards = new ArrayList<>();
        for (int i = 0; i < cardCount; i++) {
            Card card = new Card(in);
            possibleCards.add(card);
        }
        String choice = choseCardManager.choseDraftCard(possibleCards, me);
        System.out.println(choice);
    }
    private static void battlePhase(Scanner in, Board board, Legend me, int cardCount) {
        resetCardsList(board, me);
        for (int i = 0; i < cardCount; i++) {
            Card card = new Card(in);
            putCardOnRightLocation(card, board, me);
        }
        List<Card> cardsPlayable = me.getCardsPlayable();
        String actions = actionManager.computeAction(cardsPlayable, me, board);
        System.out.println(actions);
    }
    private static void initPlayers(Scanner in, Legend me, Legend opponent) {
        me.init(in);
        opponent.init(in);
    }
    private static void putCardOnRightLocation(Card card, Board board, Legend me) {
        switch(card.location) {
            case 0: me.deck.add(card);
                break;
            case 1: board.myCards.add(card);
                break;
            case -1:board.opponentCards.add(card);
                break;
        }
    }
    private static void resetCardsList(Board board, Legend me) {
        me.deck.clear();
        board.myCards.clear();
        board.opponentCards.clear();
    }
}
