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
        String[] ranks = { "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King" };

        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(suit, rank));
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
