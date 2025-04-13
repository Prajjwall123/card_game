import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter player name: ");
        String playerName = scanner.nextLine();//getting player name
        Game game = new Game(playerName);//creating game object
        game.play();//playing the game
        scanner.close();
    }
}