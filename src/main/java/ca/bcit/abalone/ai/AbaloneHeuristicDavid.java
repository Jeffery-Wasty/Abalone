package ca.bcit.abalone.ai;

import java.util.Arrays;

import ca.bcit.abalone.game.AbaloneGame;

public class AbaloneHeuristicDavid {

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


    public static HeuristicCalculator<AbaloneGame> simplePositionWeightedHeuristicDavid = (game) -> {
        int heuristic = 0;
        char[] state = game.state.getBoard();
        System.out.println(Arrays.toString(state));
        for (int i = 0; i < state.length; i++) {
            char marble = state[i];
            switch (marble) {
                case AbaloneGame.BLACK:
                    heuristic += simpleHeuristicValue(state, i);
                    break;
                case AbaloneGame.WHITE:
                    heuristic -= simpleHeuristicValue(state, i);
                    break;
            }
        }
        return heuristic;
    };

    public static int simpleHeuristicValue(char[] state, int i) {
        int neighbourAlly = 0;
        int h = POSITION_WEIGHT_MAP[i] + 30;
        byte[] destPos = AbaloneGame.LOCATION_LOOKUP_TABLE[i];
        

        return h;
    }

    public static void main(String[] args) {
        AbaloneGame game = new AbaloneGame(new AbaloneGame.State(AbaloneGame.BELGIAN_DAISY_INITIAL_STATE, 1), -1);
        System.out.println(simplePositionWeightedHeuristicDavid.getHeuristic(game));
    }

}
