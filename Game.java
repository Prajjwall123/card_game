import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Game {
    private final List<Player> players;
    private final Deck deck;
    private final Scanner scanner;
    private final int numRounds;
    private final List<RoundInfo> roundsPlayed;
    private final HighScoreManager highScoreManager;

    private static class RoundInfo {
        private final int round;
        private final String playerName;
        private final List<Card> startingHand;
        private final List<Integer> cardsRemoved;
        private final List<Card> endingHand;
        private final int score;

        RoundInfo(int round, Player player, List<Card> startingHand,
                List<Integer> cardsRemoved, List<Card> endingHand, int score) {
            this.round = round;
            this.playerName = player.getName();
            this.startingHand = new ArrayList<>(startingHand);
            this.cardsRemoved = new ArrayList<>(cardsRemoved);
            this.endingHand = new ArrayList<>(endingHand);
            this.score = score;
        }

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

    public void play() {
        for (int round = 1; round <= numRounds; round++) {
            System.out.println("\n=== Round " + round + " ===");
            deck.shuffle();
            for (Player player : players) {
                playOneRound(player, round);
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

    private void playOneRound(Player player, int roundNumber) {
        while (player.getHand().size() > 0) {
            player.getHand().removeCard(0);
        }
        dealFiveCards(player);
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

    private void dealFiveCards(Player player) {
        for (int i = 0; i < 5; i++) {
            if (deck.size() == 0) {
                deck.shuffle();
            }
            player.getHand().addCard(deck.deal());
        }
    }

    private List<Integer> chooseCardsToRemove(Player player) {
        List<Integer> toRemove;
        if (player.getName().equalsIgnoreCase("Computer")) {
            toRemove = findBestCardsToRemove(player.getHand());
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

    private void dealToThreeCards(Player player) {
        while (player.getHand().size() < 3 && deck.size() > 0) {
            player.getHand().addCard(deck.deal());
        }
    }

    public void displayReplay() {
        System.out.println("\n=== Game Replay ===");
        for (RoundInfo round : roundsPlayed) {
            round.show();
        }
    }

    public HighScoreManager getHighScoreManager() {
        return highScoreManager;
    }
}