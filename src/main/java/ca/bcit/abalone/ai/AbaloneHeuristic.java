package ca.bcit.abalone.ai;

import ca.bcit.abalone.game.AbaloneGame;

public class AbaloneHeuristic {

    public static final int[] POSITION_WEIGHT_MAP = new int[]{
            1, 1, 1, 1, 1,
            1, 2, 2, 2, 2, 1,
            1, 2, 3, 3, 3, 2, 1,
            1, 2, 3, 4, 4, 3, 2, 1,
            1, 2, 3, 4, 5, 4, 3, 2, 1,
            1, 2, 3, 4, 4, 3, 2, 1,
            1, 2, 3, 3, 3, 2, 1,
            1, 2, 2, 2, 2, 1,
            1, 1, 1, 1, 1,
    };

    public static final int[] POSITION_PUSH_MAP = new int[]{
            5, 5, 5, 5, 5,
            5, 4, 4, 4, 4, 5,
            5, 4, 3, 3, 3, 4, 5,
            5, 4, 3, 2, 2, 2, 4, 5,
            5, 4, 3, 2, 1, 2, 3, 4, 5,
            5, 4, 3, 2, 2, 3, 4, 5,
            5, 4, 3, 3, 3, 4, 5,
            5, 4, 4, 4, 4, 5,
            5, 5, 5, 5, 5,
    };

    public static HeuristicCalculator<AbaloneGame> simplePositionWeightedHeuristic = (game) -> {
        int heuristic = 0;
        char[] state = game.state.getBoard();
        for (int i = 0; i < state.length; i++) {
            char marble = state[i];
            switch (marble) {
                case AbaloneGame.BLACK:
                    heuristic += (POSITION_WEIGHT_MAP[i] + 50);
                    break;
                case AbaloneGame.WHITE:
                    heuristic -= (POSITION_WEIGHT_MAP[i] + 50);
                    break;
            }
        }

        return heuristic;
    };

    private static int countSurrounding(int index, char colour) {

        int surroundingAllyPieces = 0;

        if (AbaloneGame.LOCATION_LOOKUP_TABLE[index][0] == colour)
            surroundingAllyPieces++;
        if (AbaloneGame.LOCATION_LOOKUP_TABLE[index][1] == colour)
            surroundingAllyPieces++;
        if (AbaloneGame.LOCATION_LOOKUP_TABLE[index][2] == colour)
            surroundingAllyPieces++;
        if (AbaloneGame.LOCATION_LOOKUP_TABLE[index][3] == colour)
            surroundingAllyPieces++;
        if (AbaloneGame.LOCATION_LOOKUP_TABLE[index][4] == colour)
            surroundingAllyPieces++;
        if (AbaloneGame.LOCATION_LOOKUP_TABLE[index][5] == colour)
            surroundingAllyPieces++;

        return surroundingAllyPieces;
    }


    public static HeuristicCalculator<AbaloneGame> positionAndNotAloneHeuristic = (game) -> {
        int heuristic = 0;
        char[] state = game.state.getBoard();
        for (int i = 0; i < state.length; i++) {
            char marble = state[i];
            switch (marble) {
                case AbaloneGame.BLACK:
                    heuristic += (POSITION_WEIGHT_MAP[i] + 50);
                    heuristic += countSurrounding(i, AbaloneGame.BLACK) * 5;
                    break;
                case AbaloneGame.WHITE:
                    heuristic -= (POSITION_WEIGHT_MAP[i] + 50);
                    heuristic -= countSurrounding(i, AbaloneGame.WHITE) * 5;
                    break;
            }
        }
        return heuristic;
    };

    public static HeuristicCalculator<AbaloneGame> positionAndEnemyOutsideHeuristic = (game) -> {
        int heuristic = 0;
        char[] state = game.state.getBoard();
        for (int i = 0; i < state.length; i++) {
            char marble = state[i];
            switch (marble) {
                case AbaloneGame.BLACK:
                    heuristic += (POSITION_WEIGHT_MAP[i] + 50);
                    heuristic -= (POSITION_PUSH_MAP[i] + 30);
                    break;
                case AbaloneGame.WHITE:
                    heuristic -= (POSITION_WEIGHT_MAP[i] + 50);
                    heuristic += (POSITION_PUSH_MAP[i] + 30);
                    break;
            }
        }

        return heuristic;
    };

    public static void main(String[] args) {
        AbaloneGame game = new AbaloneGame(new AbaloneGame.State(AbaloneGame.BELGIAN_DAISY_INITIAL_STATE, 1), -1);
        System.out.println(simplePositionWeightedHeuristic.getHeuristic(game));
    }

}
