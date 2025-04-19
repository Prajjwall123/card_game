import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

public class HandTest {
    @Test
    public void testCalculateScoreBasic() {
        LinkedList<Card> cards = new LinkedList<>();
        cards.add(new Card("Hearts", "5"));
        cards.add(new Card("Clubs", "6"));
        cards.add(new Card("Spades", "3"));
        Hand hand = new Hand(cards);
        assertEquals(1, hand.calculateScore()); // score should be 15-14=1
    }

    @Test
    public void testAceScoreAdjustment() {
        LinkedList<Card> cards = new LinkedList<>();
        cards.add(new Card("Hearts", "Ace"));
        cards.add(new Card("Clubs", "9"));
        cards.add(new Card("Spades", "9"));
        Hand hand = new Hand(cards);
        assertEquals(4, hand.calculateScore()); // Ace counts as 1 here
    }

    @Test
    public void testColorBonus() {
        LinkedList<Card> cards = new LinkedList<>();
        cards.add(new Card("Hearts", "2"));
        cards.add(new Card("Diamonds", "6"));
        cards.add(new Card("Hearts", "7"));
        Hand hand = new Hand(cards);
        assertEquals(Math.abs(15 - 15) - 1, hand.calculateScore()); // color bonus
    }

    @Test
    public void testSuitBonus() {
        LinkedList<Card> cards = new LinkedList<>();
        cards.add(new Card("Spades", "5"));
        cards.add(new Card("Spades", "6"));
        cards.add(new Card("Spades", "4"));
        Hand hand = new Hand(cards);
        assertEquals(Math.abs(15 - 15) - 2 - 1, hand.calculateScore()); // suit + color bonus
    }

}
