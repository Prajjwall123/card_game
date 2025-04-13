import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HighScoreManager {
    private static final String FILE_NAME = "highscores.txt"; // high score file

    private static class HighScore {
        String playerName; // Players name
        double averageScore; // Their average score

        HighScore(String playerName, double averageScore) {
            this.playerName = playerName;
            this.averageScore = averageScore;
        }

        @Override
        public String toString() {
            return String.format("%.2f %s", averageScore, playerName);
        }
    }

    // Adds a new score to the file
    public void addScore(String playerName, int totalScore, int numRounds) {
        double averageScore = (double) totalScore / numRounds; // Get average
        averageScore = Math.round(averageScore * 100.0) / 100.0; // Round it
        List<HighScore> scores = readScores();
        scores.add(new HighScore(playerName, averageScore));
        writeScores(scores);
    }

    // Shows top 5 scores
    public void displayTopScores() {
        List<HighScore> scores = readScores();
        scores.sort(Comparator.comparingDouble(hs -> hs.averageScore)); // sotring
        List<HighScore> topScores = scores.stream().limit(5).toList();

        System.out.println("\n=== High Score Table ===");
        if (topScores.isEmpty()) {
            System.out.println("No high scores recorded yet.");
        } else {
            for (int i = 0; i < topScores.size(); i++) {
                System.out.println((i + 1) + ". " + topScores.get(i));
            }
        }
    }

    // Reads scores from the file
    private List<HighScore> readScores() {
        List<HighScore> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+", 2);
                if (parts.length == 2) {
                    try {
                        double score = Double.parseDouble(parts[0]);
                        String name = parts[1];
                        scores.add(new HighScore(name, score));
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        } catch (FileNotFoundException ignored) {
        } catch (IOException e) {
            System.err.println("Error reading high scores: " + e.getMessage());
        }
        return scores;
    }

    private void writeScores(List<HighScore> scores) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (HighScore score : scores) {
                writer.write(score.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing high scores: " + e.getMessage());
        }
    }
}