import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Reads player input

        // aks for number of players
        int numPlayers;
        do {
            System.out.print("Enter number of players (1-6): ");
            try {
                numPlayers = Integer.parseInt(scanner.nextLine()); // Get number
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
                name = "Player" + i; // if player name is not given, player+number
            }
            playerNames.add(name);
        }

        // number of rounds
        int numRounds;
        do {
            System.out.print("Enter number of rounds (1-5): ");
            try {
                numRounds = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                numRounds = 0;
            }
        } while (numRounds < 1 || numRounds > 5);

        Game game = new Game(playerNames, numRounds);
        game.play();

        System.out.print("\nWould you like to view a replay? (yes/no): ");
        String replayChoice = scanner.nextLine().trim().toLowerCase();
        if (replayChoice.equals("yes") || replayChoice.equals("y")) {
            game.displayReplay(); // Show replay
        }

        System.out.print("\nWould you like to view the high-score table? (yes/no): ");
        String highScoreChoice = scanner.nextLine().trim().toLowerCase();
        if (highScoreChoice.equals("yes") || highScoreChoice.equals("y")) {
            game.getHighScoreManager().displayTopScores(); // Show scores
        }

        scanner.close();
    }
}