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
            if (card.getcardNumber().equals("A")) {
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
        return Math.abs(15 - calculateValue());
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