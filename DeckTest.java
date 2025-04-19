import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {
    @Test
    public void testDeckCreation() {
        Deck deck = new Deck();
        assertEquals(52, deck.size());
    }

    @Test
    public void testDealCard() {
        Deck deck = new Deck();
        Card card = deck.deal();
        assertNotNull(card);
        assertEquals(51, deck.size());
    }

    @Test
    public void testDealFromEmptyDeck() {
        Deck deck = new Deck();
        while (deck.size() > 0) {
            deck.deal();
        }
        assertThrows(IllegalStateException.class, deck::deal);
    }

    @Test
    public void testShuffle() {
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();
        deck2.shuffle();
        // shoukld not be same after shuffling
        boolean different = false;
        for (int i = 0; i < 10; i++) {
            if (!deck1.deal().toString().equals(deck2.deal().toString())) {
                different = true;
                break;
            }
        }
        assertTrue(different);
    }
}