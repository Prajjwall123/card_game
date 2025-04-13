import java.util.LinkedList;
import java.util.Scanner;

public class Hand {
    private LinkedList<Card> cards; // List of cards in hand
    private boolean isComputer; // Check if its a computers hand

    public Hand(LinkedList<Card> initialCards) {
        this.cards = initialCards;
        this.isComputer = false;
    }

    public Hand(LinkedList<Card> initialCards, boolean isComputer) {
        this.cards = initialCards;
        this.isComputer = isComputer;
        if (isComputer) {
            autoTrimToThree(); // Automatically delete all other cards,to be left with 3 cards only
        }
    }

    // Trim the hand to 3 cards for player
    public void trimToThree() {
        Scanner scanner = new Scanner(System.in);
        while (cards.size() > 3) {
            System.out.println("Current Hand:");
            displayHand(); // Show current hand
            System.out.print("Enter the index (1 to " + cards.size() + ") of the card to remove: ");
            int index = scanner.nextInt();
            if (index >= 1 && index <= cards.size()) {
                cards.remove(index - 1);
            } else {
                System.out.println("Invalid index. Try again.");
            }
        }
    }

    // removeing cards to reach 3 hands for computer
    private void autoTrimToThree() {
        while (cards.size() > 3) {
            cards.removeLast(); // Remove last card until size is 3
        }
    }

    // Calculate the score for the hand
    public int calculateScore() {
        int total = 0;
        int aceCount = 0;

        // Add up all card values, handling ace later
        for (Card c : cards) {
            if (c.getvalue().equals("Ace")) {
                aceCount++;
            }
            total += c.getValue();
        }

        // if ther are aces and total>15, convert ace value from 11 to 1
        while (total > 15 && aceCount > 0) {
            total -= 10;
            aceCount--;
        }

        int score = Math.abs(15 - total); // score=15-toatl

        // Check if all cards are same color or same suit
        boolean sameColor = cards.stream().allMatch(c -> c.getColor().equals(cards.get(0).getColor()));
        boolean sameSuit = cards.stream().allMatch(c -> c.getSuit().equals(cards.get(0).getSuit()));

        if (sameColor)
            score -= 1; // Reducing score if same color
        if (sameSuit)
            score -= 2; // Reducing score if same suit

        return score;
    }

    // Display the current hand
    public void displayHand() {
        for (int i = 0; i < cards.size(); i++) {
            System.out.println((i + 1) + ". " + cards.get(i));
        }
    }

    public LinkedList<Card> getCards() {
        return cards;
    }
}
