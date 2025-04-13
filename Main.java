import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== 15from3 Game ===");
        System.out.print("Enter number of human players (1-6): ");
        int humans = scanner.nextInt();
        System.out.print("Enter number of computer players (0-6): ");
        int computers = scanner.nextInt();
        System.out.print("Enter number of rounds (1-5): ");
        int rounds = scanner.nextInt();
        scanner.nextLine();

        Game game = new Game(rounds, humans, computers);
        game.play();
    }
}
