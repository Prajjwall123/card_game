import java.util.LinkedList;

public class Game {
    private Deck deck;

    public Game() {
        deck = new Deck();
    }

    public void play() {
        System.out.println("Welcome to 15from3!");

        LinkedList<Card> initialCards = deck.deal(5);
        Hand hand = new Hand(initialCards);

        hand.trimToThree();
        System.out.println("Final Hand:");
        hand.displayHand();

        int score = hand.calculateScore();
        System.out.println("Your score is: " + score);
    }
}
