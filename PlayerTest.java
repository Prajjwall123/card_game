import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    @Test
    public void testPlayerCreation() {
        Player player = new Player("Player");
        assertEquals("Player", player.getName());
        assertEquals(0, player.getTotalScore());
        assertEquals(0, player.getHand().size());
    }

    @Test
    public void testAddScore() {
        Player player = new Player("Player");
        player.addScore(5);
        player.addScore(3);
        assertEquals(8, player.getTotalScore());
    }

    @Test
    public void testHandManagement() {
        Player player = new Player("Player");
        Card card = new Card("4", "diamond");
        player.getHand().addCard(card);
        assertEquals(1, player.getHand().size());
        player.getHand().removeCard(0);
        assertEquals(0, player.getHand().size());
    }

    @Test
    public void testPlayerScoreCalculation() {
        Player player = new Player("Player");
        player.getHand().addCard(new Card("7", "heart"));
        player.getHand().addCard(new Card("6", "spade"));
        player.getHand().addCard(new Card("3", "club"));
        // Score should be 1, because Total = 16, score = |15 - 16| = 1
        assertEquals(1, player.getHand().calculateScore());
    }
}