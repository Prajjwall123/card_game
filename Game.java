import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
            System.out.println("  Removed cards: " +
                    (removedIndices.isEmpty() ? "None"
                            : removedIndices.stream()
                                    .map(i -> String.valueOf(i + 1))
                                    .collect(Collectors.joining(" "))));
            System.out.println("  Final hand: " + formatHand(finalHand));
            System.out.println("  Score: " + score);
        }

        private String formatHand(List<Card> cards) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < cards.size(); i++) {
                sb.append(i + 1).append(": ").append(cards.get(i)).append(" ");
            }
            return sb.toString().trim();
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

        System.out.println("\n=== Final Scores ===");
        for (Player player : players) {
            System.out.println(player.getName() + ": " + player.getTotalScore());
        }

        for (Player player : players) {
            highScoreManager.addScore(player.getName(), player.getTotalScore(), numRounds);
        }
    }

    private void playRound(Player player, int roundNumber) {
        // Clear hand by removing all cards
        while (player.getHand().size() > 0) {
            player.getHand().removeCard(0);
        }

        // Deal 5 cards
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
        // Ensure hand is empty
        while (player.getHand().size() > 0) {
            player.getHand().removeCard(0);
        }
        // Deal exactly 5 cards
        for (int i = 0; i < 5; i++) {
            if (deck.size() == 0) {
                deck.shuffle(); // Reshuffle if deck runs out (rare)
            }
            player.getHand().addCard(deck.deal());
        }
    }

    private List<Integer> removeCards(Player player) {
        List<Integer> toRemove;
        if (player.getName().equalsIgnoreCase("Computer")) {
            toRemove = computeOptimalCardsToRemove(player.getHand());
            System.out.println(player.getName() + " removes cards: " +
                    toRemove.stream().map(i -> String.valueOf(i + 1)).collect(Collectors.joining(" ")));
        } else {
            System.out.println(
                    player.getName() + ", enter the numbers (1-5) of at least two cards to remove (e.g., 1 3 5): ");
            String input = scanner.nextLine();
            String[] indices = input.trim().split("\\s+");

            toRemove = new ArrayList<>();
            for (String s : indices) {
                try {
                    int index = Integer.parseInt(s) - 1;
                    if (index >= 0 && index < player.getHand().size()) {
                        toRemove.add(index);
                    }
                } catch (NumberFormatException ignored) {
                }
            }

            if (toRemove.size() < 2) {
                System.out.println("You must remove at least two cards. Try again.");
                return removeCards(player);
            }
        }

        toRemove.sort((a, b) -> b - a);
        for (int index : toRemove) {
            player.getHand().removeCard(index);
        }
        return toRemove;
    }

    private List<Integer> computeOptimalCardsToRemove(Hand hand) {
        List<Card> cards = hand.getCards();
        int n = cards.size();
        List<Integer> bestIndicesToRemove = new ArrayList<>();
        int bestScore = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    Hand tempHand = new Hand();
                    tempHand.addCard(cards.get(i));
                    tempHand.addCard(cards.get(j));
                    tempHand.addCard(cards.get(k));

                    int score = tempHand.calculateScore();
                    if (score < bestScore) {
                        bestScore = score;
                        bestIndicesToRemove.clear();
                        for (int m = 0; m < n; m++) {
                            if (m != i && m != j && m != k) {
                                bestIndicesToRemove.add(m);
                            }
                        }
                    } else if (score == bestScore) {
                        String firstSuit = tempHand.getCards().get(0).getSuit();
                        boolean sameSuit = tempHand.getCards().stream()
                                .allMatch(card -> card.getSuit().equals(firstSuit));
                        if (sameSuit) {
                            bestIndicesToRemove.clear();
                            for (int m = 0; m < n; m++) {
                                if (m != i && m != j && m != k) {
                                    bestIndicesToRemove.add(m);
                                }
                            }
                        } else {
                            String firstColor = tempHand.getCards().get(0).getColor();
                            boolean sameColor = tempHand.getCards().stream()
                                    .allMatch(card -> card.getColor().equals(firstColor));
                            if (sameColor) {
                                bestIndicesToRemove.clear();
                                for (int m = 0; m < n; m++) {
                                    if (m != i && m != j && m != k) {
                                        bestIndicesToRemove.add(m);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return bestIndicesToRemove;
    }

    private void dealToThreeCards(Player player) {
        while (player.getHand().size() < 3 && deck.size() > 0) {
            player.getHand().addCard(deck.deal());
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