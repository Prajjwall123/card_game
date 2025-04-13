import java.util.Collections;
import java.util.LinkedList;

public class Deck {
    private LinkedList<Card> cards;

    public Deck() {
        reset();
    }

    public void reset() {
        cards = new LinkedList<>();
        String[] suits = { "Hearts", "Diamonds", "Clubs", "Spades" };
        for (String suit : suits) {
            for (int value = 1; value <= 10; value++) {
                cards.add(new Card(suit, value));
            }
        }
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public LinkedList<Card> deal(int n) {
        LinkedList<Card> hand = new LinkedList<>();
        for (int i = 0; i < n && !cards.isEmpty(); i++) {
            hand.add(cards.removeFirst());
        }
        return hand;
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
