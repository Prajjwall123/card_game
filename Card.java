public class Card {
    private final String rank;
    private final String suit;
    private final String color;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
        this.color = (suit.equals("heart") || suit.equals("diamond")) ? "Red" : "Black";
    }

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    public String getColor() {
        return color;
    }

    public int getValue() {
        switch (rank) {
            case "A":
                return 11;
            case "2":
                return 2;
            case "3":
                return 3;
            case "4":
                return 4;
            case "5":
                return 5;
            case "6":
                return 6;
            case "7":
                return 7;
            case "8":
                return 8;
            case "9":
                return 9;
            case "10":
            case "J":
            case "Q":
            case "K":
                return 10;
            default:
                throw new IllegalStateException("Invalid rank: " + rank);
        }
    }

    @Override
    public String toString() {
        return rank + suit;
    }
}