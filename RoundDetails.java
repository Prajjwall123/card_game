import java.util.LinkedList;
import java.util.List;

public class RoundDetails {
    private List<PlayerRoundDetails> playerRoundDetails;

    public RoundDetails(List<PlayerRoundDetails> playerRoundDetails) {
        this.playerRoundDetails = playerRoundDetails;
    }

    public List<PlayerRoundDetails> getPlayerRoundDetails() {
        return playerRoundDetails;
    }
}

class PlayerRoundDetails {
    private String name;
    private LinkedList<Card> initialHand;
    private LinkedList<Card> removedCards;
    private int score;

    public PlayerRoundDetails(String name, LinkedList<Card> initialHand, LinkedList<Card> removedCards, int score) {
        this.name = name;
        this.initialHand = initialHand;
        this.removedCards = removedCards;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public LinkedList<Card> getInitialHand() {
        return initialHand;
    }

    public LinkedList<Card> getRemovedCards() {
        return removedCards;
    }

    public int getScore() {
        return score;
    }
}
