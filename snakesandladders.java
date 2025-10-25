import java.util.Map;
import java.util.Scanner;

public class snakesandladders {

    // Making snakes and ladders static so all methods can access
    static Map<Integer, Integer> ladder = Map.of(
            10, 20,
            15, 25,
            30, 40,
            45, 50
    );

    static Map<Integer, Integer> snake = Map.of(
            13, 8,
            19, 14,
            25, 20,
            47, 42
    );

    public static void main(String[] args) {
        startGame();
    }

    public static void startGame() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter number of players:");
        int numPlayers = sc.nextInt();
        Player[] players = new Player[numPlayers];

        sc.nextLine(); //used to fix an error between nextInt and nextLine

        for (int j = 0; j < numPlayers; j++) {
            System.out.println("Enter Player " + (j + 1) + " Name:");
            String name = sc.nextLine();
            players[j] = new Player(name);
        }

        System.out.println("Welcome to Snakes & Ladders!");

        int turn = 0;
        boolean gameOver = false;

        while (!gameOver) {
            Player current = players[turn];
            System.out.println("\n" + current.name + "'s turn");
            System.out.println("1. Roll The Dice");
            System.out.println("2. Exit");
            int option = sc.nextInt();

            switch (option) {
                case 1:
                    current.position = rollingDice(current.position, 6);
                    current.position = checkLadder(current.position);
                    current.position = checkSnake(current.position);

                    if (current.position >= 50) {
                        System.out.println(current.name + " Won the Game!");
                        gameOver = true;
                        break;
                    }

                    showPosition(current);
                    break;

                case 2:
                    gameOver = true;
                    break;

                default:
                    System.out.println("Invalid option. Choose 1 or 2.");
            }

            turn = (turn + 1) % numPlayers;
        }

        sc.close();
    }

    static int rollingDice(int pos, int diceSides) {
        System.out.print("Rolling the Dice");
        try {
            for (int j = 0; j < 3; j++) {
                System.out.print(".");
                Thread.sleep(300); // pause 300ms for animation
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        int dice = (int) (Math.random() * diceSides) + 1;
        System.out.println("\nYou Rolled " + dice);
        pos += dice;
        return pos;
    }


    static int checkLadder(int pos) {
        if (ladder.containsKey(pos)) {
            pos = ladder.get(pos);
            System.out.println("You Hit a Ladder! Jumping to " + pos);
        }
        return pos;
    }

    static int checkSnake(int pos) {
        if (snake.containsKey(pos)) {
            pos = snake.get(pos);
            System.out.println("You Hit a Snake! Going down to " + pos);
        }
        return pos;
    }

    static void showPosition(Player player) {
        System.out.println(player.name + " is at position " + player.position);
    }

}

class Player {
    String name;
    int position;

    Player(String name) {
        this.name = name;
        this.position = 1;
    }
}
