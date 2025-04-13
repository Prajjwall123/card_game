import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Game {
    private final List<Player> players; // List of player objects
    private final Deck deck; // The deck of cards
    private final Scanner scanner;
    private final int numRounds; // Number of rounds
    private final List<RoundInfo> roundsPlayed; // Records track of rounds
    private final HighScoreManager highScoreManager; // Handles scores

    private static class RoundInfo {
        private final int round; // Round number(ehich round it is)
        private final String playerName; // Player's name
        private final List<Card> startingHand; // Cards at start
        private final List<Integer> cardsRemoved; // Cards removed
        private final List<Card> endingHand; // Cards at end
        private final int score; // Round score

        RoundInfo(int round, Player player, List<Card> startingHand,
                List<Integer> cardsRemoved, List<Card> endingHand, int score) {
            this.round = round;
            this.playerName = player.getName();
            this.startingHand = new ArrayList<>(startingHand);
            this.cardsRemoved = new ArrayList<>(cardsRemoved);
            this.endingHand = new ArrayList<>(endingHand);
            this.score = score;
        }

        // Shows round details
        void show() {
            System.out.println("Round " + round + ", Player: " + playerName);
            System.out.println("  Starting hand: " + formatCards(startingHand));
            System.out.print("  Removed cards: ");
            if (cardsRemoved.isEmpty()) {
                System.out.println("None");
            } else {
                for (int i = 0; i < cardsRemoved.size(); i++) {
                    System.out.print((cardsRemoved.get(i) + 1));
                    if (i < cardsRemoved.size() - 1)
                        System.out.print(" ");
                }
                System.out.println();
            }
            System.out.println("  Final hand: " + formatCards(endingHand));
            System.out.println("  Score: " + score);
        }

        // Makes card list look nice
        private String formatCards(List<Card> cards) {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < cards.size(); i++) {
                result.append(i + 1).append(": ").append(cards.get(i));
                if (i < cards.size() - 1)
                    result.append(" ");
            }
            return result.toString();
        }
    }

    // Sets up the game
    public Game(List<String> playerNames, int numRounds) {
        if (playerNames.size() > 6) {
            throw new IllegalArgumentException("Maximum 6 players allowed");
        }
        players = new ArrayList<>();
        for (String name : playerNames) {
            players.add(new Player(name));
        }
        deck = new Deck();
        scanner = new Scanner(System.in);
        this.numRounds = numRounds;
        roundsPlayed = new ArrayList<>();
        highScoreManager = new HighScoreManager();
    }

    // Plays all rounds
    public void play() {
        for (int round = 1; round <= numRounds; round++) {
            System.out.println("\n=== Round " + round + " ===");
            deck.shuffle();
            for (Player player : players) {
                playOneRound(player, round);
            }
        }

        // Show final scores
        System.out.println("\n=== Final Scores ===");
        for (Player player : players) {
            System.out.println(player.getName() + ": " + player.getTotalScore());
        }

        // Save scores
        for (Player player : players) {
            highScoreManager.addScore(player.getName(), player.getTotalScore(), numRounds);
        }
    }

    // Plays one round for a player
    private void playOneRound(Player player, int roundNumber) {
        while (player.getHand().size() > 0) {
            player.getHand().removeCard(0);
        }
        dealFiveCards(player); // Deal 5 cards
        List<Card> startingHand = new ArrayList<>(player.getHand().getCards());
        System.out.println(player.getName() + "'s hand: " + formatHand(player.getHand()));

        List<Integer> removedCards = chooseCardsToRemove(player);
        dealToThreeCards(player);
        List<Card> endingHand = new ArrayList<>(player.getHand().getCards());
        System.out.println(player.getName() + "'s final hand: " + formatHand(player.getHand()));

        int score = player.getHand().calculateScore();
        player.addScore(score);
        System.out.println(player.getName() + "'s score for this round: " + score);

        roundsPlayed.add(new RoundInfo(roundNumber, player, startingHand, removedCards, endingHand, score));
    }

    // Shows the hand nicely
    private String formatHand(Hand hand) {
        List<Card> cards = hand.getCards();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < cards.size(); i++) {
            result.append(i + 1).append(": ").append(cards.get(i));
            if (i < cards.size() - 1)
                result.append(" ");
        }
        return result.toString();
    }

    // Deals 5 cards to a player
    private void dealFiveCards(Player player) {
        for (int i = 0; i < 5; i++) {
            if (deck.size() == 0) {
                deck.shuffle();
            }
            player.getHand().addCard(deck.deal());
        }
    }

    // Lets player pick which cards cards to remove
    private List<Integer> chooseCardsToRemove(Player player) {
        List<Integer> toRemove;
        if (player.getName().equalsIgnoreCase("Computer")) {
            toRemove = findBestCardsToRemove(player.getHand()); // Computer picks
            System.out.print(player.getName() + " removes cards: ");
            for (int i = 0; i < toRemove.size(); i++) {
                System.out.print((toRemove.get(i) + 1));
                if (i < toRemove.size() - 1)
                    System.out.print(" ");
            }
            System.out.println();
        } else {
            toRemove = new ArrayList<>();
            boolean validInput = false;
            while (!validInput) {
                System.out.println(
                        player.getName() + ", enter numbers (1-5) to remove at least two cards (e.g., 1 3 5): ");
                String input = scanner.nextLine();
                String[] numbers = input.trim().split("\\s+");

                toRemove.clear();
                for (String num : numbers) {
                    try {
                        int index = Integer.parseInt(num) - 1;
                        if (index >= 0 && index < 5 && !toRemove.contains(index)) {
                            toRemove.add(index);
                        }
                    } catch (NumberFormatException ignored) {
                    }
                }

                if (toRemove.size() >= 2) {
                    validInput = true;
                } else {
                    System.out.println("Please remove at least two valid cards.");
                }
            }
        }

        toRemove.sort(Collections.reverseOrder());
        for (int index : toRemove) {
            player.getHand().removeCard(index);
        }
        return toRemove;
    }

    // Computer picks best cards to remove
    private List<Integer> findBestCardsToRemove(Hand hand) {
        List<Card> cards = hand.getCards();
        List<Integer> bestToRemove = new ArrayList<>();
        int lowestScore = Integer.MAX_VALUE;

        for (int i = 0; i < cards.size(); i++) {
            for (int j = i + 1; j < cards.size(); j++) {
                for (int k = j + 1; k < cards.size(); k++) {
                    Hand testHand = new Hand();
                    testHand.addCard(cards.get(i));
                    testHand.addCard(cards.get(j));
                    testHand.addCard(cards.get(k));

                    int score = testHand.calculateScore();
                    if (score < lowestScore) {
                        lowestScore = score;
                        bestToRemove.clear();
                        for (int m = 0; m < cards.size(); m++) {
                            if (m != i && m != j && m != k) {
                                bestToRemove.add(m);
                            }
                        }
                    }
                }
            }
        }
        return bestToRemove;
    }

    // Deals cards to get 3
    private void dealToThreeCards(Player player) {
        while (player.getHand().size() < 3 && deck.size() > 0) {
            player.getHand().addCard(deck.deal());
        }
    }

    // Shows game replay
    public void displayReplay() {
        System.out.println("\n=== Game Replay ===");
        for (RoundInfo round : roundsPlayed) {
            round.show();
        }
    }

    // Gets score manager
    public HighScoreManager getHighScoreManager() {
        return highScoreManager;
    }
}