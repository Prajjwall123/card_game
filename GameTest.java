import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

public class GameTest {
    @Test
    public void testGameInitialization() {
        Game game = new Game(2, 1, 1); // 2 rounds, 1 human, 1 computer
        assertNotNull(game);
    }

    @Test
    public void testReplayPromptHandled() {
        // testing inputs like: name, card indices, and yes option to replay
        String input = "Player1\n2\n3\nyes\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Game game = new Game(1, 1, 0);
        game.play();
        System.setIn(System.in);
    }

}
