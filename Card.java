public class Card {
    private final String cardNumber;
    private final String cardType;

    public Card(String cardNumber, String cardType) {
        this.cardNumber = cardNumber;
        this.cardType = cardType;
    }

    public String getcardNumber() {
        return cardNumber;
    }

    public String getcardType() {
        return cardType;
    }

    public int getValue() {
        switch (cardNumber) {
            case "A"://can be 1 or 11
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
                throw new IllegalStateException("Invalid cardNumber: " + cardNumber);
        }
    }

    @Override
    public String toString() {
        return cardNumber + cardType;
    }
}