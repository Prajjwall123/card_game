import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private final Player player;
    private final Deck deck;
    private final Scanner scanner;

    public Game(String playerName) {
        this.player = new Player(playerName);
        this.deck = new Deck();
        this.scanner = new Scanner(System.in);
    }

    public void play() {
        dealInitialHand();
        System.out.println(player.getName() + "'s hand: " + player.getHand());

        removeCards();
        dealToThreeCards();
        System.out.println(player.getName() + "'s final hand: " + player.getHand());

        int score = player.getHand().calculateScore();
        System.out.println(player.getName() + "'s score: " + score);
    }

    private void dealInitialHand() {
        for (int i = 0; i < 5; i++) {
            player.getHand().addCard(deck.deal());
        }
    }

    private void removeCards() {
        System.out.println("Enter the numbers (1-5) of at least two cards to remove (e.g., 1 3 5): ");
        String input = scanner.nextLine();
        String[] indices = input.trim().split("\\s+");

        List<Integer> toRemove = new ArrayList<>();
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
            removeCards();
            return;
        }

        toRemove.sort((a, b) -> b - a);
        for (int index : toRemove) {
            player.getHand().removeCard(index);
        }
    }

    private void dealToThreeCards() {
        while (player.getHand().size() < 3 && deck.size() > 0) {
            player.getHand().addCard(deck.deal());
        }
    }
}