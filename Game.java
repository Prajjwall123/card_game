import java.util.*;
import java.io.*;

public class Game {
    private Deck deck;
    private final int rounds;
    private final int numPlayers;
    private final int numComputers;
    private final List<String> playerNames;
    private final Map<String, Integer> totalScores;

    private final Map<Integer, List<PlayerAction>> replayLog; // reacording actions of players for replay

    // Constructor to start the game
    public Game(int rounds, int numPlayers, int numComputers) {
        this.rounds = rounds;
        this.numPlayers = numPlayers;
        this.numComputers = numComputers;
        this.deck = new Deck(); // new deck
        this.playerNames = new ArrayList<>();
        this.totalScores = new HashMap<>();
        this.replayLog = new HashMap<>();
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to 15from3!\n");

        // Add human players
        for (int i = 1; i <= numPlayers; i++) {
            System.out.print("Enter name for Player " + i + ": ");
            String name = scanner.nextLine();
            playerNames.add(name);
            totalScores.put(name, 0);
        }

        // Add computers
        for (int i = 1; i <= numComputers; i++) {
            String name = "Computer" + i;
            playerNames.add(name);
            totalScores.put(name, 0);
        }

        // Play rounds
        for (int round = 1; round <= rounds; round++) {
            System.out.println("\n--- Round " + round + " ---");
            deck.reset();

            List<PlayerAction> roundActions = new ArrayList<>();

            // Players take turns
            for (String name : playerNames) {
                System.out.println("\n" + name + "'s turn:");
                LinkedList<Card> dealt = deck.deal(5);
                LinkedList<Card> initialHandCopy = new LinkedList<>(dealt);
                boolean isComputer = name.startsWith("Computer");
                Hand hand = new Hand(dealt, isComputer);

                List<Card> removedCards = new ArrayList<>();

                if (!isComputer) {
                    while (hand.getCards().size() > 3) {
                        hand.displayHand();
                        System.out
                                .print("Enter the index (1 to " + hand.getCards().size() + ") of the card to remove: ");
                        int index = scanner.nextInt();
                        if (index >= 1 && index <= hand.getCards().size()) {
                            removedCards.add(hand.getCards().get(index - 1));
                            hand.getCards().remove(index - 1);
                        } else {
                            System.out.println("Invalid index. Try again.");
                        }
                    }
                } else {
                    while (hand.getCards().size() > 3) {
                        removedCards.add(hand.getCards().getLast());
                        hand.getCards().removeLast();
                    }
                }

                System.out.println("Final Hand:");
                hand.displayHand();

                int score = hand.calculateScore();
                System.out.println("Score: " + score);
                totalScores.put(name, totalScores.get(name) + score);

                // Save actions for replay later
                roundActions.add(new PlayerAction(name, initialHandCopy, removedCards,
                        new LinkedList<>(hand.getCards()), score));
            }

            replayLog.put(round, roundActions);
        }

        // Show final scores
        System.out.println("\n=== Final Scores ===");
        playerNames.sort(Comparator.comparingInt(totalScores::get));
        for (String name : playerNames) {
            System.out.println(name + ": " + totalScores.get(name));
        }

        updateHighScores();

        // Ask for replay
        scanner.nextLine();
        System.out.print("\nWould you like to view the replay? (yes/no): ");
        String replayChoice = scanner.nextLine().trim();
        if (replayChoice.equalsIgnoreCase("yes")) {
            viewReplay();
        }

    }

    // Update high scores to a file
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

            // Add new scores
            for (String name : playerNames) {
                allScores.add(totalScores.get(name) + " - " + name);
            }

            allScores.sort(Comparator.comparingInt(s -> Integer.parseInt(s.split(" - ")[0])));
            List<String> top5 = allScores.subList(0, Math.min(5, allScores.size()));

            // Write to file
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

    // Show the replay of the game
    private void viewReplay() {
        System.out.println("\n=== Game Replay ===");
        for (int round : replayLog.keySet()) {
            System.out.println("\n--- Round " + round + " ---");
            for (PlayerAction action : replayLog.get(round)) {
                System.out.println("\nPlayer: " + action.name);
                System.out.println("Initial Hand:");
                action.displayHand(action.initialHand);
                System.out.println("Removed Cards:");
                action.displayHand(action.removedCards);
                System.out.println("Final Hand:");
                action.displayHand(action.finalHand);
                System.out.println("Score: " + action.score);
            }
        }
    }

    // Class to store actions in each round
    private static class PlayerAction {
        String name;
        LinkedList<Card> initialHand;
        List<Card> removedCards;
        LinkedList<Card> finalHand;
        int score;

        // Constructor to save the actions
        public PlayerAction(String name, LinkedList<Card> initialHand, List<Card> removedCards,
                LinkedList<Card> finalHand, int score) {
            this.name = name;
            this.initialHand = initialHand;
            this.removedCards = removedCards;
            this.finalHand = finalHand;
            this.score = score;
        }

        // Display cards
        public void displayHand(List<Card> hand) {
            if (hand.isEmpty()) {
                System.out.println("(none)");
            }
            for (Card card : hand) {
                System.out.println(card);
            }
        }
    }
}
