import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HandTest {
    @Test
    public void testAddAndRemoveCard() {
        Hand hand = new Hand();
        Card card = new Card("5", "heart");
        hand.addCard(card);
        assertEquals(1, hand.size());
        hand.removeCard(0);
        assertEquals(0, hand.size());
    }

    @Test
    public void testCalculateValueSimple() {
        Hand hand = new Hand();
        hand.addCard(new Card("5", "heart"));
        hand.addCard(new Card("6", "spade"));
        hand.addCard(new Card("3", "club"));
        assertEquals(14, hand.calculateValue());
    }

    @Test
    public void testCalculateValueWithAceLow() {
        Hand hand = new Hand();
        hand.addCard(new Card("A", "heart"));
        hand.addCard(new Card("9", "spade"));
        hand.addCard(new Card("8", "club"));
        // 9 + 8 = 17, Ace = 1 to stay closer to 15
        assertEquals(18, hand.calculateValue());
    }

    @Test
    public void testCalculateScore() {
        Hand hand = new Hand();
        hand.addCard(new Card("5", "heart"));
        hand.addCard(new Card("6", "spade"));
        hand.addCard(new Card("3", "club"));
        // Total = 14, score = |15 - 14| = 1, no bonuses
        assertEquals(1, hand.calculateScore());
    }

    @Test
    public void testSameColorBonus() {
        Hand hand = new Hand();
        hand.addCard(new Card("5", "heart"));
        hand.addCard(new Card("6", "diamond"));
        hand.addCard(new Card("3", "heart"));
        // Total = 14, base = |15 - 14| = 1, same color = -1
        assertEquals(0, hand.calculateScore());
    }

    @Test
    public void testSameSuitBonus() {
        Hand hand = new Hand();
        hand.addCard(new Card("5", "heart"));
        hand.addCard(new Card("6", "heart"));
        hand.addCard(new Card("3", "heart"));
        // Total = 14, base = |15 - 14| = 1, same suit = -3
        assertEquals(0, hand.calculateScore());
    }
}