import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTest {

    @Test
    public void testCardCreation() {
        Card card = new Card("Diamonds", "Jack");// careting new card, a jack of diamond
        assertEquals("Diamonds", card.getSuit());
        assertEquals("Jack", card.getvalue());
        assertEquals(10, card.getValue());
    }

    @Test
    public void testAceValue() {
        Card ace = new Card("Spades", "Ace");// testing value of Ace
        assertEquals(11, ace.getValue());// epecting 11
    }

    @Test
    public void testCardColor() {
        Card card = new Card("Clubs", "7");// tesing color of 7 of clubs
        assertEquals("Black", card.getColor());// exprecting black
    }

    @Test
    public void testToString() {
        Card card = new Card("Hearts", "Queen");// testing card to string
        assertEquals("Queen of Hearts", card.toString());
    }
}
