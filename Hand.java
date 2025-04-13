import java.util.ArrayList;
import java.util.List;

public class Hand {
    private final List<Card> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

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

    public int calculateValue() {
        int total = 0;
        int aces = 0;

        for (Card card : cards) {
            if (card.getRank().equals("A")) {
                aces++;
            } else {
                total += card.getValue();
            }
        }

        for (int i = 0; i < aces; i++) {
            if (total + 11 <= 15 || total + 11 <= 21) {
                total += 11;
            } else {
                total += 1;
            }
        }

        return total;
    }

    public int calculateScore() {
        int baseScore = Math.abs(15 - calculateValue());
        int bonus = calculateBonus();
        return Math.max(0, baseScore + bonus);
    }

    private int calculateBonus() {
        if (cards.size() < 2) {
            return 0;
        }

        String firstColor = cards.get(0).getColor();
        boolean sameColor = cards.stream().allMatch(card -> card.getColor().equals(firstColor));

        String firstSuit = cards.get(0).getSuit();
        boolean sameSuit = cards.stream().allMatch(card -> card.getSuit().equals(firstSuit));

        if (sameSuit) {
            return -3;
        } else if (sameColor) {
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cards.size(); i++) {
            sb.append(i + 1).append(": ").append(cards.get(i)).append(" ");
        }
        return sb.toString();
    }
}