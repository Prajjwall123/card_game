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
        // Dealing first 5 cards
        for (int i = 0; i < 5; i++) {
            player.getHand().addCard(deck.deal());
        }
        System.out.println(player.getName() + "'s hand: " + player.getHand());

        // Removing cards
        removeCards();

        // Dealing new cards until hand has 3 cards
        while (player.getHand().size() < 3 && deck.size() > 0) {
            player.getHand().addCard(deck.deal());
        }
        System.out.println(player.getName() + "'s final hand: " + player.getHand());

        // Showing final score
        System.out.println(player.getName() + "'s score: " + player.getHand().calculateScore());
    }

    private void removeCards() {
        List<Integer> cardsToRemove = new ArrayList<>();

        while (cardsToRemove.size() < 2) {
            System.out.println("Enter the numbers (1-5) of at least two cards to remove (e.g., 1 3 5): ");
            String input = scanner.nextLine();

            // Reseting the list for new attempt
            cardsToRemove.clear();

            // Parsing input
            String[] numbers = input.trim().split("\\s+");
            for (String num : numbers) {
                try {
                    int cardIndex = Integer.parseInt(num) - 1;
                    if (cardIndex >= 0 && cardIndex < player.getHand().size()) {
                        cardsToRemove.add(cardIndex);
                    }
                } catch (NumberFormatException ignored) {
                    // Skip invalid numbers
                }
            }

            if (cardsToRemove.size() < 2) {
                System.out.println("You must remove at least two cards. Try again.");
            }
        }

        // Removing cards from highest index to lowest to avoid shifting issues
        cardsToRemove.sort((a, b) -> b - a);
        for (int index : cardsToRemove) {
            player.getHand().removeCard(index);
        }
    }
}