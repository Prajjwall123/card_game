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
        List<Integer> toRemove;
        if (player.getName().equalsIgnoreCase("Computer")) {
            // Computer player: Choose optimal cards to remove
            toRemove = computeOptimalCardsToRemove(player.getHand());
            System.out.println(player.getName() + " removes cards: " +
                    toRemove.stream().map(i -> String.valueOf(i + 1)).collect(Collectors.joining(" ")));
        } else {
            // Human player: Prompt for input
            System.out.println(
                    player.getName() + ", enter the numbers (1-5) of at least two cards to remove (e.g., 1 3 5): ");
            String input = scanner.nextLine();
            String[] indices = input.trim().split("\\s+");

            toRemove = new ArrayList<>();
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
        }

        // Sort in reverse order to avoid index shifting
        toRemove.sort((a, b) -> b - a);
        for (int index : toRemove) {
            player.getHand().removeCard(index);
        }
    }

    private List<Integer> computeOptimalCardsToRemove(Hand hand) {
        List<Card> cards = hand.getCards();
        int n = cards.size(); // Should be 5
        List<Integer> bestIndicesToRemove = new ArrayList<>();
        int bestScore = Integer.MAX_VALUE;

        // Try all combinations of keeping 3 cards (removing 2)
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    // Simulate keeping cards i, j, k
                    Hand tempHand = new Hand();
                    tempHand.addCard(cards.get(i));
                    tempHand.addCard(cards.get(j));
                    tempHand.addCard(cards.get(k));

                    int score = tempHand.calculateScore();
                    if (score < bestScore) {
                        bestScore = score;
                        bestIndicesToRemove.clear();
                        // Add indices to remove (all except i, j, k)
                        for (int m = 0; m < n; m++) {
                            if (m != i && m != j && m != k) {
                                bestIndicesToRemove.add(m);
                            }
                        }
                    } else if (score == bestScore) {
                        // Tiebreaker: Prefer same-suit or same-color for bonuses
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
}