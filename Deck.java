import java.util.Collections;
import java.util.LinkedList;

public class Deck {
    private LinkedList<Card> cards;

    // Constructor resets deck when created
    public Deck() {
        reset();
    }

    // reset teh deck with all cards
    public void reset() {
        cards = new LinkedList<>();
        String[] suits = { "Hearts", "Diamonds", "Clubs", "Spades" };
        String[] ranks = { "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King" };

        // Add all cards to deck
        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(suit, rank));
            }
        }

        shuffle();
    }

    // suffle the deck
    public void shuffle() {
        Collections.shuffle(cards);
    }

    // Deal n number of cards
    public LinkedList<Card> deal(int n) {
        LinkedList<Card> hand = new LinkedList<>();
        for (int i = 0; i < n && !cards.isEmpty(); i++) {
            hand.add(cards.removeFirst());
        }
        return hand;
    }

    // Check if teh deck is emty
    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
