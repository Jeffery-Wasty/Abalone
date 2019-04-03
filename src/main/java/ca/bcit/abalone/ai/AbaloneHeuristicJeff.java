package ca.bcit.abalone.ai;

import ca.bcit.abalone.game.AbaloneGame;

public class AbaloneHeuristicJeff {

    private static final int BUMPED_WEIGHT = 50;    // High weight for piece to prioritize bumping off.
    private static final int CENTER_WEIGHT = 1;     // Adjust the weight given to controlling the center
    private static final int ADJACENT_WEIGHT = 1;   // Adjust the weight given to having adjacent pieces.

    private static final int[] POSITION_MAP = new int[] {
            1, 1, 1, 1, 1,
            1, 2, 2, 2, 2, 1,
            1, 2, 5, 5, 5, 2, 1,
            1, 2, 5, 8, 8, 5, 2, 1,
            1, 2, 5, 8, 10, 8, 5, 2, 1,
            1, 2, 5, 8, 8, 5, 2, 1,
            1, 2, 5, 5, 5, 2, 1,
            1, 2, 2, 2, 2, 1,
            1, 1, 1, 1, 1,
    };

    public static HeuristicCalculator<AbaloneGame> simplePositionWeightedHeuristic = (game) -> {
        int heuristic = 0;
        char[] state = game.state.getBoard();

        for (int i = 0; i < state.length; i++) {
            char marble = state[i];
            final int CENTER = CENTER_WEIGHT * POSITION_MAP[i];

            switch (marble) {
                case AbaloneGame.BLACK:
                    heuristic += (CENTER + ADJACENT_WEIGHT * ADJ(marble, state, i, AbaloneGame.BLACK) + BUMPED_WEIGHT);
                    break;
                case AbaloneGame.WHITE:
                    heuristic -= (CENTER + ADJACENT_WEIGHT * ADJ(marble, state, i, AbaloneGame.WHITE) + BUMPED_WEIGHT);
                    break;
            }
        }
        return heuristic;
    };

    private static int ADJ(char marble, char[] state, int i, char c) {
        int adjacent = 0;
        for (byte ba: AbaloneGame.LOCATION_LOOKUP_TABLE[i]) {
            if (ba != -1)
                if (state[ba] == c || marble == c)
                    ++adjacent;
        }
        return adjacent;
    }

    public static void main(String[] args) {
        AbaloneGame game = new AbaloneGame(new AbaloneGame.State(AbaloneGame.BELGIAN_DAISY_INITIAL_STATE, 1), -1);
        System.out.println(simplePositionWeightedHeuristic.getHeuristic(game));
    }

}
