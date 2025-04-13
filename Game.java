import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private final List<Player> players;
    private final Deck deck;
    private final Scanner scanner;
    private final int numRounds;

    public Game(List<String> playerNames, int numRounds) {
        if (playerNames.size() > 6) {
            throw new IllegalArgumentException("Maximum 6 players allowed");
        }
        this.players = new ArrayList<>();
        for (String name : playerNames) {
            players.add(new Player(name));
        }
        this.deck = new Deck();
        this.scanner = new Scanner(System.in);
        this.numRounds = numRounds;
    }

    public void play() {
        for (int round = 1; round <= numRounds; round++) {
            System.out.println("\n=== Round " + round + " ===");
            deck.shuffle(); // Reshuffle each round
            for (Player player : players) {
                playRound(player);
            }
        }

        // Report final scores
        System.out.println("\n=== Final Scores ===");
        for (Player player : players) {
            System.out.println(player.getName() + ": " + player.getTotalScore());
        }
    }

    private void playRound(Player player) {
        // Clear previous hand
        player.getHand().getCards().clear();

        // Deal 5 cards
        dealInitialHand(player);
        System.out.println(player.getName() + "'s hand: " + player.getHand());

        // Remove cards
        removeCards(player);

        // Deal to 3 cards
        dealToThreeCards(player);
        System.out.println(player.getName() + "'s final hand: " + player.getHand());

        // Calculate and store score
        int score = player.getHand().calculateScore();
        player.addScore(score);
        System.out.println(player.getName() + "'s score for this round: " + score);
    }

    private void dealInitialHand(Player player) {
        for (int i = 0; i < 5; i++) {
            player.getHand().addCard(deck.deal());
        }
    }

    private void removeCards(Player player) {
        System.out.println(
                player.getName() + ", enter the numbers (1-5) of at least two cards to remove (e.g., 1 3 5): ");
        String input = scanner.nextLine();
        String[] indices = input.trim().split("\\s+");

        List<Integer> toRemove = new ArrayList<>();
        for (String s : indices) {
            try {
                int index = Integer.parseInt(s) - 1; // Convert to 0-based index
                if (index >= 0 && index < player.getHand().size()) {
                    toRemove.add(index);
                }
            } catch (NumberFormatException ignored) {
            }
        }

        if (toRemove.size() < 2) {
            System.out.println("You must remove at least two cards. Try again.");
            removeCards(player);
            return;
        }

        // Sort in reverse order to avoid index shifting
        toRemove.sort((a, b) -> b - a);
        for (int index : toRemove) {
            player.getHand().removeCard(index);
        }
    }

    private void dealToThreeCards(Player player) {
        while (player.getHand().size() < 3 && deck.size() > 0) {
            player.getHand().addCard(deck.deal());
        }
    }
}