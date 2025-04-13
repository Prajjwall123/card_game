import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Game {
    private final List<Player> players;
    private final Deck deck;
    private final Scanner scanner;
    private final int numRounds;
    private final List<RoundRecord> roundRecords;
    private final HighScoreManager highScoreManager;

    private static class RoundRecord {
        private final int roundNumber;
        private final Player player;
        private final List<Card> initialHand;
        private final List<Integer> removedIndices;
        private final List<Card> finalHand;
        private final int score;

        public RoundRecord(int roundNumber, Player player, List<Card> initialHand,
                List<Integer> removedIndices, List<Card> finalHand, int score) {
            this.roundNumber = roundNumber;
            this.player = player;
            this.initialHand = new ArrayList<>(initialHand);
            this.removedIndices = new ArrayList<>(removedIndices);
            this.finalHand = new ArrayList<>(finalHand);
            this.score = score;
        }

        public void display() {
            System.out.println("Round " + roundNumber + ", Player: " + player.getName());
            System.out.println("  Initial hand: " + formatHand(initialHand));
            System.out.println("  Removed cards: " + formatIndices(removedIndices));
            System.out.println("  Final hand: " + formatHand(finalHand));
            System.out.println("  Score: " + score);
        }

        private String formatHand(List<Card> cards) {
            return cards.stream()
                    .map(card -> (cards.indexOf(card) + 1) + ": " + card)
                    .collect(Collectors.joining(" "));
        }

        private String formatIndices(List<Integer> indices) {
            return indices.isEmpty() ? "None"
                    : indices.stream()
                            .map(i -> String.valueOf(i + 1))
                            .collect(Collectors.joining(" "));
        }
    }

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
        this.roundRecords = new ArrayList<>();
        this.highScoreManager = new HighScoreManager();
    }

    public void play() {
        for (int round = 1; round <= numRounds; round++) {
            System.out.println("\n=== Round " + round + " ===");
            deck.shuffle();
            for (Player player : players) {
                playRound(player, round);
            }
        }

        displayFinalScores();
        saveHighScores();
    }

    private void playRound(Player player, int roundNumber) {
        player.getHand().getCards().clear();
        dealInitialHand(player);
        List<Card> initialHand = new ArrayList<>(player.getHand().getCards());
        System.out.println(player.getName() + "'s hand: " + player.getHand());

        List<Integer> removedIndices = removeCards(player);
        dealToThreeCards(player);
        List<Card> finalHand = new ArrayList<>(player.getHand().getCards());
        System.out.println(player.getName() + "'s final hand: " + player.getHand());

        int score = player.getHand().calculateScore();
        player.addScore(score);
        System.out.println(player.getName() + "'s score for this round: " + score);

        roundRecords.add(new RoundRecord(roundNumber, player, initialHand, removedIndices, finalHand, score));
    }

    private void dealInitialHand(Player player) {
        for (int i = 0; i < 5; i++) {
            if (deck.size() == 0) {
                deck.shuffle();
            }
            player.getHand().addCard(deck.deal());
        }
    }

    private List<Integer> removeCards(Player player) {
        if (player.getName().equalsIgnoreCase("Computer")) {
            return computeOptimalCardsToRemove(player.getHand());
        }

        List<Integer> toRemove = new ArrayList<>();
        while (toRemove.size() < 2) {
            System.out.println(
                    player.getName() + ", enter the numbers (1-5) of at least two cards to remove (e.g., 1 3 5): ");
            String input = scanner.nextLine();

            toRemove.clear();
            for (String num : input.trim().split("\\s+")) {
                try {
                    int index = Integer.parseInt(num) - 1;
                    if (index >= 0 && index < player.getHand().size()) {
                        toRemove.add(index);
                    }
                } catch (NumberFormatException ignored) {
                }
            }

            if (toRemove.size() < 2) {
                System.out.println("You must remove at least two cards. Try again.");
            }
        }

        Collections.sort(toRemove, Collections.reverseOrder());
        for (int index : toRemove) {
            player.getHand().removeCard(index);
        }
        return toRemove;
    }

    private List<Integer> computeOptimalCardsToRemove(Hand hand) {
        List<Card> cards = hand.getCards();
        List<Integer> bestIndices = new ArrayList<>();
        int bestScore = Integer.MAX_VALUE;

        for (int i = 0; i < cards.size(); i++) {
            for (int j = i + 1; j < cards.size(); j++) {
                for (int k = j + 1; k < cards.size(); k++) {
                    Hand tempHand = new Hand();
                    tempHand.addCard(cards.get(i));
                    tempHand.addCard(cards.get(j));
                    tempHand.addCard(cards.get(k));

                    int score = tempHand.calculateScore();
                    if (score < bestScore) {
                        bestScore = score;
                        bestIndices.clear();
                        for (int m = 0; m < cards.size(); m++) {
                            if (m != i && m != j && m != k) {
                                bestIndices.add(m);
                            }
                        }
                    }
                }
            }
        }

        return bestIndices;
    }

    private void dealToThreeCards(Player player) {
        while (player.getHand().size() < 3 && deck.size() > 0) {
            player.getHand().addCard(deck.deal());
        }
    }

    private void displayFinalScores() {
        System.out.println("\n=== Final Scores ===");
        for (Player player : players) {
            System.out.println(player.getName() + ": " + player.getTotalScore());
        }
    }

    private void saveHighScores() {
        for (Player player : players) {
            highScoreManager.addScore(player.getName(), player.getTotalScore(), numRounds);
        }
    }

    public void displayReplay() {
        System.out.println("\n=== Game Replay ===");
        for (RoundRecord record : roundRecords) {
            record.display();
        }
    }

    public HighScoreManager getHighScoreManager() {
        return highScoreManager;
    }
}