public class Card {
    private final String suit;
    private final String value;

    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public String getvalue() {
        return value;
    }

    public String getColor() {
        return (suit.equals("Hearts") || suit.equals("Diamonds")) ? "Red" : "Black";
        // if ist heart or diamond= red
        // if its spades or clubs= black
    }

    public int getValue() {
        switch (value) {
            case "Jack":
            case "Queen":
            case "King":
                return 10;// teh value of king jack and queen is 10 each and for ace 11(can change to 1 in
                          // Game.java)
            case "Ace":
                return 11;
            default:
                return Integer.parseInt(value);// or else value is took from its value(2-10)
        }
    }

    @Override
    public String toString() {
        return value + " of " + suit;
    }
}
