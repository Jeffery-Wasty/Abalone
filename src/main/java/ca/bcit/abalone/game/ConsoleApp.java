package ca.bcit.abalone.game;

import ca.bcit.abalone.ai.AlphaBetaAI;

import java.util.Scanner;

public class ConsoleApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AbaloneGame game = new AbaloneGame(new AbaloneGame.State(AbaloneGame.GERMAN_DAISY_INITIAL_STATE, 1), 7);
        AlphaBetaAI<Character, AbaloneGame.State, AbaloneGame.Action> ai = new AlphaBetaAI<>();
        while (game.actions() != null && game.actions().length != 0) {
            System.out.println(game);
            System.out.println(game.getPlayer() + "'s turn. Input your action: {n, location, direction}");
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
                long time = System.currentTimeMillis();
                AbaloneGame.Action aiAction = ai.play(game);
                time = System.currentTimeMillis() - time;
                System.out.println(time + "ms");
                if (aiAction != null) {
                    game = game.result(aiAction);
                } else {
                    System.out.println("No More Actions");
                    break;
                }
            }
        }
        System.out.println("Program Exited");
    }

}
