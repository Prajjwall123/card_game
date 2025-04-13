public class Player {
    private final String name;
    private final Hand hand;
    private int totalScore;

    public Player(String name) {
        this.name = name;
        this.hand = new Hand();
        this.totalScore = 0;
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