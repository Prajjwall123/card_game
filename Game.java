import java.util.*;
import java.io.*;

public class Game {
    private Deck deck;
    private final int rounds;
    private final int numPlayers;
    private final int numComputers;
    private final List<String> playerNames;
    private final Map<String, Integer> totalScores;
    private final List<RoundDetails> roundDetailsList;  // List to store round details

    public Game(int rounds, int numPlayers, int numComputers) {
        this.rounds = rounds;
        this.numPlayers = numPlayers;
        this.numComputers = numComputers;
        this.deck = new Deck();
        this.playerNames = new ArrayList<>();
        this.totalScores = new HashMap<>();
        this.roundDetailsList = new ArrayList<>();
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to 15from3!\n");

        for (int i = 1; i <= numPlayers; i++) {
            System.out.print("Enter name for Player " + i + ": ");
            String name = scanner.nextLine();
            playerNames.add(name);
            totalScores.put(name, 0);
        }
        for (int i = 1; i <= numComputers; i++) {
            String name = "Computer" + i;
            playerNames.add(name);
            totalScores.put(name, 0);
        }

        for (int round = 1; round <= rounds; round++) {
            System.out.println("\n--- Round " + round + " ---");
            deck.reset();
            List<PlayerRoundDetails> playerRounds = new ArrayList<>();

            for (String name : playerNames) {
                System.out.println("\n" + name + "'s turn:");
                LinkedList<Card> dealt = deck.deal(5);
                Hand hand = new Hand(dealt, name.startsWith("Computer"));
                if (!name.startsWith("Computer")) {
                    hand.trimToThree();
                }
                System.out.println("Final Hand:");
                hand.displayHand();

                int score = hand.calculateScore();
                System.out.println("Score: " + score);
                totalScores.put(name, totalScores.get(name) + score);

                // Store round details
                playerRounds.add(new PlayerRoundDetails(name, hand.getInitialHand(), hand.getRemovedCards(), score));
            }

            roundDetailsList.add(new RoundDetails(playerRounds));  // Add round details to the list
        }

        System.out.println("\n=== Final Scores ===");
        playerNames.sort(Comparator.comparingInt(totalScores::get));
        for (String name : playerNames) {
            System.out.println(name + ": " + totalScores.get(name));
        }

        updateHighScores();

        // Ask if the player wants to see the replay
        System.out.print("\nWould you like to view the replay? (yes/no): ");
        String replayChoice = scanner.nextLine();
        if (replayChoice.equalsIgnoreCase("yes")) {
            showReplay();
        }
    }

    private void showReplay() {
        for (int round = 0; round < roundDetailsList.size(); round++) {
            System.out.println("\n--- Replay of Round " + (round + 1) + " ---");
            RoundDetails roundDetails = roundDetailsList.get(round);
            for (PlayerRoundDetails playerRound : roundDetails.getPlayerRoundDetails()) {
                System.out.println(playerRound.getName() + "'s initial hand: " + playerRound.getInitialHand());
                System.out.println(playerRound.getName() + "'s removed cards: " + playerRound.getRemovedCards());
                System.out.println(playerRound.getName() + "'s score: " + playerRound.getScore());
            }
        }
    }

    private void updateHighScores() {
        try {
            File file = new File("highscores.txt");
            List<String> allScores = new ArrayList<>();
            if (file.exists()) {
                Scanner reader = new Scanner(file);
                while (reader.hasNextLine()) {
                    allScores.add(reader.nextLine());
                }
                reader.close();
            }

            for (String name : playerNames) {
                allScores.add(totalScores.get(name) + " - " + name);
            }

            allScores.sort(Comparator.comparingInt(s -> Integer.parseInt(s.split(" - ")[0])));
            List<String> top5 = allScores.subList(0, Math.min(5, allScores.size()));

            FileWriter writer = new FileWriter(file);
            for (String s : top5) {
                writer.write(s + "\n");
            }
            writer.close();

            System.out.println("\nTop 5 High Scores:");
            for (String s : top5) {
                System.out.println(s);
            }
        } catch (IOException e) {
            System.out.println("Failed to update high scores.");
        }
    }
}
