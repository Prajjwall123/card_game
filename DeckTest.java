import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeckTest {
    @Test
    public void testDeckHas52Cards() {
        Deck deck = new Deck();
        assertEquals(52, deck.deal(52).size());//checking size of deck, deck should have 52 cards
    }

    @Test
    public void testShuffleChangesOrder() {
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();
        assertNotEquals(deck1.deal(5).toString(), deck2.deal(5).toString());
    }

    @Test
    public void testDealFromEmptyDeck() {
        Deck deck = new Deck();
        deck.deal(52);
        assertTrue(deck.deal(1).isEmpty());
    }

}
