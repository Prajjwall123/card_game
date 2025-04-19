import java.util.ArrayList;
import java.util.List;

public class Hand {
    private final List<Card> cards;// list of card objects

    public Hand() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    // Removes a card by its position
    public void removeCard(int index) {
        if (index >= 0 && index < cards.size()) {
            cards.remove(index);
        }
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }

    public int size() {
        return cards.size();
    }

    // total value of the hand
    public int calculateValue() {
        int total = 0;
        int aces = 0;

        for (Card card : cards) {
            if (card.getcardNumber().equals("A")) {
                aces++;// Count aces separately
            } else {
                total += card.getValue();// Add other card values
            }
        }

        for (int i = 0; i < aces; i++) {
            // Aces are 11 if it fits(total value is less than or equal to 15), else 1
            if (total + 11 <= 15 || total + 11 <= 21) {
                total += 11;
            } else {
                total += 1;
            }
        }

        return total;
    }

    // Works out the score for the game
    public int calculateScore() {
        int baseScore = Math.abs(15 - calculateValue());// Score based on how close to 15
        int bonus = calculateBonus();// Add bonus if there are any
        return Math.max(0, baseScore + bonus);
    }

    // Checks for bonus points
    private int calculateBonus() {
        if (cards.size() < 2) {
            return 0;
        }

        String firstColor = cards.get(0).getColor();
        boolean sameColor = cards.stream().allMatch(card -> card.getColor().equals(firstColor));

        String firstcardType = cards.get(0).getcardType();
        boolean samecardType = cards.stream().allMatch(card -> card.getcardType().equals(firstcardType));

        if (samecardType) {
            return -3;// 3 bonus for same card type(suit)
        } else if (sameColor) {
            return -1;// 1 bonus for same color
        }
        return 0;// No bonus
    }

    // Shows the hand like "1: Aheart 2: 5spade"
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cards.size(); i++) {
            sb.append(i + 1).append(": ").append(cards.get(i)).append(" ");
        }
        return sb.toString();
    }
}