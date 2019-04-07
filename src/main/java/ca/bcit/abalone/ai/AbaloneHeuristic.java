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

    public static final HeuristicCalculator<AbaloneGame> SIMPLE_POSITION_WEIGHTED_HEURISTIC = (game, rootGame, info) -> {
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

    public static int simpleHeuristicValue(char[] state, int i) {
        int h = (POSITION_WEIGHT_MAP[i] + 30);
        byte[] pos = AbaloneGame.LOCATION_LOOKUP_TABLE[i];
        for (byte p : pos) {
            if (p != -1 && state[p] == state[i]) {
                h++;
            }
        }
        return h;
    }

    public static void main(String[] args) {
        AbaloneGame game = new AbaloneGame(new AbaloneGame.State(AbaloneGame.BELGIAN_DAISY_INITIAL_STATE, 1), -1);
//        System.out.println(SIMPLE_POSITION_WEIGHTED_HEURISTIC.getHeuristic(game, game));
    }

}
