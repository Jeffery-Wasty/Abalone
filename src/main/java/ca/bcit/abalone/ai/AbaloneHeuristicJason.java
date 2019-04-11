package ca.bcit.abalone.ai;

import ca.bcit.abalone.game.AbaloneGame;

public class AbaloneHeuristicJason {

    public static class AdditionalInfo {
        public final int layout;

        public AdditionalInfo(int layout) {
            this.layout = layout;
        }
    }

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

    private static final int[] POSITION_WEIGHT_MAP2 = new int[]{
            1, 1, 1, 1, 1,
            1, 2, 2, 2, 2, 1,
            1, 2, 5, 5, 5, 2, 1,
            1, 2, 5, 8, 8, 5, 2, 1,
            1, 2, 5, 8, 9, 8, 5, 2, 1,
            1, 2, 5, 8, 8, 5, 2, 1,
            1, 2, 5, 5, 5, 2, 1,
            1, 2, 2, 2, 2, 1,
            1, 1, 1, 1, 1,
    };

    private static final int[] POSITION_WEIGHT_MAP3 = new int[]{
            1, 1, 1, 1, 1,
            1, 2, 2, 2, 2, 1,
            1, 2, 4, 4, 4, 2, 1,
            1, 2, 4, 6, 6, 5, 2, 1,
            1, 2, 4, 6, 7, 6, 5, 2, 1,
            1, 2, 4, 6, 6, 6, 2, 1,
            1, 2, 4, 4, 5, 2, 1,
            1, 2, 2, 2, 2, 1,
            1, 1, 1, 1, 1,
    };

    public static final byte[][] LOCATION_LOOKUP_TABLE = new byte[][]{
            {-1, -1, -1, 5, 6, 1},
            {0, -1, -1, 6, 7, 2},
            {1, -1, -1, 7, 8, 3},
            {2, -1, -1, 8, 9, 4},
            {3, -1, -1, 9, 10, -1},
            {-1, -1, 0, 11, 12, 6},
            {5, 0, 1, 12, 13, 7},
            {6, 1, 2, 13, 14, 8},
            {7, 2, 3, 14, 15, 9},
            {8, 3, 4, 15, 16, 10},
            {9, 4, -1, 16, 17, -1},
            {-1, -1, 5, 18, 19, 12},
            {11, 5, 6, 19, 20, 13},
            {12, 6, 7, 20, 21, 14},
            {13, 7, 8, 21, 22, 15},
            {14, 8, 9, 22, 23, 16},
            {15, 9, 10, 23, 24, 17},
            {16, 10, -1, 24, 25, -1},
            {-1, -1, 11, 26, 27, 19},
            {18, 11, 12, 27, 28, 20},
            {19, 12, 13, 28, 29, 21},
            {20, 13, 14, 29, 30, 22},
            {21, 14, 15, 30, 31, 23},
            {22, 15, 16, 31, 32, 24},
            {23, 16, 17, 32, 33, 25},
            {24, 17, -1, 33, 34, -1},
            {-1, -1, 18, -1, 35, 27},
            {26, 18, 19, 35, 36, 28},
            {27, 19, 20, 36, 37, 29},
            {28, 20, 21, 37, 38, 30},
            {29, 21, 22, 38, 39, 31},
            {30, 22, 23, 39, 40, 32},
            {31, 23, 24, 40, 41, 33},
            {32, 24, 25, 41, 42, 34},
            {33, 25, -1, 42, -1, -1},
            {-1, 26, 27, -1, 43, 36},
            {36, 27, 28, 43, 44, 37},
            {36, 28, 29, 44, 45, 38},
            {37, 29, 30, 45, 46, 39},
            {38, 30, 31, 46, 47, 40},
            {39, 31, 32, 47, 48, 41},
            {40, 32, 33, 48, 49, 42},
            {41, 33, 34, 49, -1, -1},
            {-1, 35, 36, -1, 50, 44},
            {43, 36, 37, 50, 51, 45},
            {44, 37, 38, 51, 52, 46},
            {45, 38, 39, 52, 53, 47},
            {46, 39, 40, 53, 54, 48},
            {47, 40, 41, 54, 55, 49},
            {48, 41, 42, 55, -1, -1},
            {-1, 43, 44, -1, 56, 51},
            {50, 44, 45, 56, 57, 52},
            {51, 45, 46, 57, 58, 53},
            {52, 46, 47, 58, 59, 54},
            {53, 47, 48, 59, 60, 55},
            {54, 48, 49, 60, -1, -1},
            {-1, 50, 51, -1, -1, 57},
            {56, 51, 52, -1, -1, 58},
            {57, 52, 53, -1, -1, 59},
            {58, 53, 54, -1, -1, 60},
            {59, 54, 55, -1, -1, -1},
    };

    public static HeuristicCalculator<AbaloneGame> simplePositionWeightedHeuristic = (game, rootGame, info) -> {
        int heuristic = 0;
        char[] state = game.state.getBoard();
        for (int i = 0; i < state.length; i++) {
            switch (state[i]) {
                case AbaloneGame.BLACK:
                    heuristic += simpleHeuristicValue(state, i, rootGame.getPlayer(), info.layout);
                    break;
                case AbaloneGame.WHITE:
                    heuristic -= simpleHeuristicValue(state, i, rootGame.getPlayer(), info.layout);
                    break;
            }
        }

        return heuristic;
    };

    public static int simpleHeuristicValue(char[] state, int pos, char playerMarble, int layout) {
        final int MARBLE_POINT = 100;
        final int PLAYER_BONUS = 10;
        double factor = calculateFactor(playerMarble, layout);
        int[] position_map = getPositionMap(playerMarble, layout);

        int h = position_map[pos] * 2;
        byte[] destPos = LOCATION_LOOKUP_TABLE[pos];

        char allyMarble = state[pos];
        boolean isPlayer = (playerMarble == allyMarble);
        boolean sixStar = true;

        for (int i = 0; i < destPos.length; i++) {
            int nextPos = destPos[i];
            if (nextPos != -1 && state[nextPos] == allyMarble) {
                h++;
            } else {
                sixStar = false;
            }
        }

        if (sixStar) {
            h++;
        }

        if (!isPlayer) {
            h *= factor;
        } else {
            h += PLAYER_BONUS;
        }

        h += MARBLE_POINT;

        return h;
    }

    private static int[] getPositionMap(char playerMarble, int layout) {
        int[] map = POSITION_WEIGHT_MAP;
        if (playerMarble == '@' && layout == 1) {
            map = POSITION_WEIGHT_MAP2;
        } else if (playerMarble == '@' && layout == 2) {
            map = POSITION_WEIGHT_MAP2;
        } else if (playerMarble == '@' && layout == 3) {
            map = POSITION_WEIGHT_MAP3;
        } else if (playerMarble == 'O' && layout == 1) {
            map = POSITION_WEIGHT_MAP;
        } else if (playerMarble == 'O' && layout == 2) {
            map = POSITION_WEIGHT_MAP;
        } else if (playerMarble == 'O' && layout == 3) {
            map = POSITION_WEIGHT_MAP;
        }

        return map;
    }

    private static double calculateFactor(char playerMarble, int layout) {
        double factor = 1.0;
        if (playerMarble == '@' && layout == 1) {
            factor = 1.2;
        } else if (playerMarble == '@' && layout == 2) {
            factor = 1.3;
        } else if (playerMarble == '@' && layout == 3) {
            factor = 0.9;
        } else if (playerMarble == 'O' && layout == 1) {
            factor = 1.4;
        } else if (playerMarble == 'O' && layout == 2) {
            factor = 1.5;
        } else if (playerMarble == 'O' && layout == 3) {
            factor = 1.4;
        }
//        System.out.println("Factor: " + factor);
        return factor;
    }


//    public static int simpleHeuristicValue2(char[] state, int pos, boolean isPlayer, int layout) {
//        int h = POSITION_WEIGHT_MAP2[pos] * 2;
//        byte[] destPos = LOCATION_LOOKUP_TABLE[pos];
//        char playerState = state[pos];
//        char opponentState = (playerState == '@') ? 'O' : '@';
//        int numOfAlly = 0;
//        int numOfEnmiy = 0;
//
//        for (int i = 0; i < destPos.length; i++) {
//            int nextPos = destPos[i];
//            if (nextPos != -1 && state[nextPos] == playerState) {
//                h++;
//                numOfAlly++;
//            } else if (nextPos != -1 && state[nextPos] == opponentState) {
//                numOfEnmiy++;
//            }
//        }
//
//        if (numOfAlly == 6 || numOfEnmiy == 6) {
//            h++;
//        }
//
//        if (!isPlayer) {
//            h *= AGGRESSIVE_FACTOR;
//        } else {
//            h += PLAYER_BONUS;
//        }
//
//        h += MARBLE_POINT;
//
//        return h;
//    }


    public static void main(String[] args) {
        AbaloneGame game = new AbaloneGame(new AbaloneGame.State(AbaloneGame.BELGIAN_DAISY_INITIAL_STATE, 2), -1);
    }

}
