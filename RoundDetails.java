import java.util.LinkedList;
import java.util.List;

public class RoundDetails {
    private List<PlayerRoundDetails> playerRoundDetails; // List of players round info

    public RoundDetails(List<PlayerRoundDetails> playerRoundDetails) {
        this.playerRoundDetails = playerRoundDetails;
    }

    public List<PlayerRoundDetails> getPlayerRoundDetails() {
        return playerRoundDetails;
    }
}

class PlayerRoundDetails {
    private String name; // Player name
    private LinkedList<Card> initialHand; // Player's starting cards
    private LinkedList<Card> removedCards; // Cards player removed
    private int score; // Player's score in round

    public PlayerRoundDetails(String name, LinkedList<Card> initialHand, LinkedList<Card> removedCards, int score) {
        this.name = name;
        this.initialHand = initialHand;
        this.removedCards = removedCards;
        this.score = score;
    }

    // Get player name
    public String getName() {
        return name;
    }

    // Get the starting hand of the player
    public LinkedList<Card> getInitialHand() {
        return initialHand;
    }

    // Get the removed cards of the player
    public LinkedList<Card> getRemovedCards() {
        return removedCards;
    }

    // Get score of player in round
    public int getScore() {
        return score;
    }
}
