public class Card {
    private final String suit;
    private final String rank;

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    public String getColor() {
        return (suit.equals("Hearts") || suit.equals("Diamonds")) ? "Red" : "Black";
        //if ist heart or diamond= red
        //if its spades or clubs= black
    }

    public int getValue() {
        switch (rank) {
            case "Jack":
            case "Queen":
            case "King":
                return 10;//teh value of king jack and queen is 10 each and for ace 11(can change to 1 in Game.java)
            case "Ace":
                return 11; 
            default:
                return Integer.parseInt(rank);//or else value is took from its rank(2-10)
        }
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
