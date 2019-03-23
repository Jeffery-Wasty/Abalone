package ca.bcit.abalone.ai;

import ca.bcit.abalone.game.AbaloneGame;
import ca.bcit.abalone.game.Game;

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

    public static HeuristicCalculator<Game<Character, AbaloneGame.State, AbaloneGame.Action>> simplePositionWeightedHeuristic = (game) -> {
        int heuristic = 0;
        char[] state = game.state.getBoard();
        for (int i = 0; i < state.length; i++) {
            char marble = state[i];
            switch (marble) {
                case AbaloneGame.BLACK:
                    heuristic += POSITION_WEIGHT_MAP[i];
                case AbaloneGame.WHITE:
                    heuristic -= POSITION_WEIGHT_MAP[i];
            }
        }
        return heuristic;
    };

}
