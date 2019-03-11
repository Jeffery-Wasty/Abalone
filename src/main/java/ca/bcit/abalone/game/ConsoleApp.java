package ca.bcit.abalone.game;

import java.util.Scanner;

public class ConsoleApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AbaloneGame game = new AbaloneGame();
        while (true) {
            System.out.println(game);
            System.out.println(game.player + "'s turn. Input your action: {n, location, direction}");
            String line = scanner.nextLine();
            String[] paramsStr = line.split(" ");
            if (paramsStr.length != 3) {
                break;
            }
            AbaloneGame.Action gameAction = game.isValidAction(new AbaloneAction(Integer.parseInt(paramsStr[0]),
                    Integer.parseInt(paramsStr[1]),
                    Integer.parseInt(paramsStr[2])));
            if (gameAction == null) {
                System.out.println("Invalid Action");
            } else {
                game = game.result(gameAction);
            }
        }
        System.out.println("Program Exited");
    }

}
