public class Card {
    private String suit;
    private int value;
    private String color;

    public Card(String suit, int value) {
        this.suit = suit;
        this.value = value;
        if (suit.equals("Hearts") || suit.equals("Diamonds")) {
            this.color = "Red";
        } else {
            this.color = "Black";
        }
    }

    public String getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return value + " of " + suit + " (" + color + ")";
    }
}
