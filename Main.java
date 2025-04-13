import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get number of players
        int numPlayers;
        do {
            System.out.print("Enter number of players (1-6): ");
            try {
                numPlayers = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                numPlayers = 0;
            }
        } while (numPlayers < 1 || numPlayers > 6);

        // Get player names
        List<String> playerNames = new ArrayList<>();
        for (int i = 1; i <= numPlayers; i++) {
            System.out.print("Enter name for player " + i + ": ");
            String name = scanner.nextLine();
            if (name.trim().isEmpty()) {
                name = "Player" + i;
            }
            playerNames.add(name);
        }

        // Get number of rounds
        int numRounds;
        do {
            System.out.print("Enter number of rounds (1-5): ");
            try {
                numRounds = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                numRounds = 0;
            }
        } while (numRounds < 1 || numRounds > 5);

        // Start game
        Game game = new Game(playerNames, numRounds);
        game.play();

        scanner.close();
    }
}