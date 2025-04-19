import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CardTest {
    @Test
    public void testCardCreation() {
        Card card = new Card("A", "heart");
        assertEquals("A", card.getcardNumber());
        assertEquals("heart", card.getcardType());
        assertEquals("Red", card.getColor());
    }

    @Test
    public void testCardValueAce() {
        Card card = new Card("A", "spade");
        assertEquals(11, card.getValue());
    }

    @Test
    public void testCardValueFaceCard() {
        Card card = new Card("K", "club");
        assertEquals(10, card.getValue());
    }

    @Test
    public void testCardValueNumber() {
        Card card = new Card("7", "diamond");
        assertEquals(7, card.getValue());
    }

    @Test
    public void testCardToString() {
        Card card = new Card("Q", "spade");
        assertEquals("Qspade", card.toString());
    }

    @Test
    public void testInvalidCardNumber() {
        assertThrows(IllegalStateException.class, () -> new Card("X", "heart").getValue());
    }
}