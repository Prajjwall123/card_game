public class Player {
    private final String name;// name
    private final Hand hand;// cards
    private int totalScore;// score

    // Sets up a new player
    public Player(String name) {
        this.name = name;
        this.hand = new Hand();
        this.totalScore = 0;// Start score at 0
    }

    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void addScore(int score) {
        totalScore += score;
    }
}