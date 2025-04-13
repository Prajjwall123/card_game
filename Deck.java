import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> cards; // list of card objects

    // Builds a new deck
    public Deck() {
        cards = new ArrayList<>();
        String[] cardTypes = { "spade", "heart", "diamond", "club" };
        String[] cardNumbers = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
        for (String cardType : cardTypes) {
            for (String cardNumber : cardNumbers) {
                cards.add(new Card(cardNumber, cardType)); // Add each card to deck
            }
        }
        shuffle(); // Mix up the deck
    }

    // Shuffles the cards
    public void shuffle() {
        Collections.shuffle(cards);
    }

    // Deals one card
    public Card deal() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Deck is empty");
        }
        return cards.remove(0);
    }

    // Tells how many cards are left
    public int size() {
        return cards.size();
    }
}