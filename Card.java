public class Card {
    private final String cardNumber; 
    private final String cardType; // like heart or spade
    private final String color; // either red or black

    // Sets up a new card
    public Card(String cardNumber, String cardType) {
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        // Hearts or diamonds are red, others are black
        this.color = (cardType.equals("heart") || cardType.equals("diamond")) ? "Red" : "Black";
    }

    public String getcardNumber() {
        return cardNumber;
    }

    public String getcardType() {
        return cardType;
    }

    public String getColor() {
        return color;
    }

    // Gives the card's value for games
    public int getValue() {
        switch (cardNumber) {
            case "A":
                return 11; // Ace is 11, but can also be 1
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
                return 10; // These are 10
            default:
                throw new IllegalStateException("Bad card number: " + cardNumber);
        }
    }

    // Shows the card like "Aheart"
    @Override
    public String toString() {
        return cardNumber + cardType;
    }
}