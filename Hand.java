import java.util.LinkedList;
import java.util.Scanner;

public class Hand {
    private LinkedList<Card> cards;
    private boolean isComputer;
    private LinkedList<Card> initialHand;
    private LinkedList<Card> removedCards;

    public Hand(LinkedList<Card> initialCards) {
        this.cards = initialCards;
        this.isComputer = false;
        this.initialHand = new LinkedList<>(initialCards); // Store the initial hand
        this.removedCards = new LinkedList<>();
    }

    public Hand(LinkedList<Card> initialCards, boolean isComputer) {
        this(initialCards);
        this.isComputer = isComputer;
        if (isComputer)
            autoTrimToThree();
    }

    public void trimToThree() {
        Scanner scanner = new Scanner(System.in);
        while (cards.size() > 3) {
            System.out.println("Current Hand:");
            displayHand();
            System.out.print("Enter the index (1 to " + cards.size() + ") of the card to remove: ");
            int index = scanner.nextInt();
            if (index >= 1 && index <= cards.size()) {
                Card removedCard = cards.remove(index - 1);
                removedCards.add(removedCard); // Track removed card
            } else {
                System.out.println("Invalid index. Try again.");
            }
        }
    }

    private void autoTrimToThree() {
        while (cards.size() > 3) {
            Card removedCard = cards.removeLast();
            removedCards.add(removedCard); // Track removed card
        }
    }

    public int calculateScore() {
        int total = 0;
        for (Card c : cards) {
            total += c.getValue();
        }

        int score = Math.abs(15 - total);

        boolean sameColor = cards.stream().allMatch(c -> c.getColor().equals(cards.get(0).getColor()));
        boolean sameSuit = cards.stream().allMatch(c -> c.getSuit().equals(cards.get(0).getSuit()));

        if (sameColor)
            score -= 1;
        if (sameSuit)
            score -= 2;

        return score;
    }

    public void displayHand() {
        for (int i = 0; i < cards.size(); i++) {
            System.out.println((i + 1) + ". " + cards.get(i));
        }
    }

    public LinkedList<Card> getCards() {
        return cards;
    }

    public LinkedList<Card> getInitialHand() {
        return initialHand;
    }

    public LinkedList<Card> getRemovedCards() {
        return removedCards;
    }
}
