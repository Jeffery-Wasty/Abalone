package ca.bcit.abalone.game;

import java.util.ArrayList;
import java.util.Arrays;

public class AbaloneGame extends Game<char[], AbaloneAction> {

    public static final char BLACK = '@';
    public static final char WHITE = 'O';
    public static final char EMPTY = '+';
    public static final char OUT_OF_BOARD = '!';

    public static final char[] INITIAL_STATE = new char[]{
            'O', 'O', 'O', 'O', 'O',
            'O', 'O', 'O', 'O', 'O', 'O',
            '+', '+', 'O', 'O', 'O', '+', '+',
            '+', '+', '+', '+', '+', '+', '+', '+',
            '+', '+', '+', '+', '+', '+', '+', '+', '+',
            '+', '+', '+', '+', '+', '+', '+', '+',
            '+', '+', '@', '@', '@', '+', '+',
            '@', '@', '@', '@', '@', '@',
            '@', '@', '@', '@', '@',
    };

    public AbaloneGame() {
        super(AbaloneGame.INITIAL_STATE);
    }

    public AbaloneGame(char[] state) {
        super(state);
    }

    public static final byte[][] LOCATION_LOOKUP_TABLE = new byte[][]{
            {-1, -1, -1, 1, 6, 5,},
            {0, -1, -1, 2, 7, 6,},
            {1, -1, -1, 3, 8, 7,},
            {2, -1, -1, 4, 9, 8,},
            {3, -1, -1, -1, 10, 9,},
            {-1, -1, 0, 6, 12, 11,},
            {5, 0, 1, 7, 13, 12,},
            {6, 1, 2, 8, 14, 13,},
            {7, 2, 3, 9, 15, 14,},
            {8, 3, 4, 10, 16, 15,},
            {9, 4, -1, -1, 17, 16,},
            {-1, -1, 5, 12, 19, 18,},
            {11, 5, 6, 13, 20, 19,},
            {12, 6, 7, 14, 21, 20,},
            {13, 7, 8, 15, 22, 21,},
            {14, 8, 9, 16, 23, 22,},
            {15, 9, 10, 17, 24, 23,},
            {16, 10, -1, -1, 25, 24,},
            {-1, -1, 11, 19, 27, 26,},
            {18, 11, 12, 20, 28, 27,},
            {19, 12, 13, 21, 29, 28,},
            {20, 13, 14, 22, 30, 29,},
            {21, 14, 15, 23, 31, 30,},
            {22, 15, 16, 24, 32, 31,},
            {23, 16, 17, 25, 33, 32,},
            {24, 17, -1, -1, 34, 33,},
            {-1, -1, 18, 27, 35, -1,},
            {26, 18, 19, 28, 36, 35,},
            {27, 19, 20, 29, 37, 36,},
            {28, 20, 21, 30, 38, 37,},
            {29, 21, 22, 31, 39, 38,},
            {30, 22, 23, 32, 40, 39,},
            {31, 23, 24, 33, 41, 40,},
            {32, 24, 25, 34, 42, 41,},
            {33, 25, -1, -1, -1, 42,},
            {-1, 26, 27, 36, 43, -1,},
            {36, 27, 28, 37, 44, 43,},
            {36, 28, 29, 38, 45, 44,},
            {37, 29, 30, 39, 46, 45,},
            {38, 30, 31, 40, 47, 46,},
            {39, 31, 32, 41, 48, 47,},
            {40, 32, 33, 42, 49, 48,},
            {41, 33, 34, -1, -1, 49,},
            {-1, 35, 36, 44, 50, -1,},
            {43, 36, 37, 45, 51, 50,},
            {44, 37, 38, 46, 52, 51,},
            {45, 38, 39, 47, 53, 52,},
            {46, 39, 40, 48, 54, 53,},
            {47, 40, 41, 49, 55, 54,},
            {48, 41, 42, -1, -1, 55,},
            {-1, 43, 44, 51, 56, -1,},
            {50, 44, 45, 52, 57, 56,},
            {51, 45, 46, 53, 58, 57,},
            {52, 46, 47, 54, 59, 58,},
            {53, 47, 48, 55, 60, 59,},
            {54, 48, 49, -1, -1, 60,},
            {-1, 50, 51, 57, -1, -1,},
            {56, 51, 52, 58, -1, -1,},
            {57, 52, 53, 59, -1, -1,},
            {58, 53, 54, 60, -1, -1,},
            {59, 54, 55, -1, -1, -1,},
    };

    /**
     * [first_check, second_check] = SIDE_MOVE_DIRECTION[MOVE_DIRECTION]
     */
    public static final int[][] SIDE_MOVE_DIRECTION = new int[][]{
            {AbaloneAction.UP_LEFT, AbaloneAction.UP_RIGHT}, // LEFT
            {AbaloneAction.UP_RIGHT, AbaloneAction.RIGHT},// UP_LEFT
            {AbaloneAction.RIGHT, AbaloneAction.DOWN_RIGHT}, // UP_RIGHT,
            {AbaloneAction.DOWN_RIGHT, AbaloneAction.DOWN_LEFT}, // RIGHT
            {AbaloneAction.DOWN_LEFT, AbaloneAction.LEFT},  // DOWN_RIGHT
            {AbaloneAction.LEFT, AbaloneAction.UP_LEFT}   // DOWN_LEFT
    };

    // Enumerate all the 6 directions for each marbles on the board
    public AbaloneAction[] actions(char[] state) {
        ArrayList<AbaloneAction> validActions = new ArrayList<>();
        for (int loc = 0; loc < state.length; loc++) {
            for (int i = 1; i <= 3; i++) {
                for (int j = 0; j < 6; j++) {
                    AbaloneAction action = new AbaloneAction(i, loc, j);
                    if (isValidAction(action)) {
                        validActions.add(action);
                    }
                }
            }
        }
        return validActions.toArray(new AbaloneAction[0]);
    }

    public static final String[][][] VALID_PUSHES = new String[][][]{
            { // white
                    {},
                    {"O+"},
                    {"OO+"},
                    {"OO@+", "OO@!", "OOO+"},
                    {"OOO@+", "OOO@!"},
                    {"OOO@@+", "OOO@@!"}
            },
            { // black
                    {},
                    {"@+"},
                    {"@@+"},
                    {"@@O+", "@@O!", "@@@+"},
                    {"@@@O+", "@@@O!"},
                    {"@@@OO+", "@@@OO!"}
            }
    };

    public static final int LONGEST_PUSH = VALID_PUSHES[0].length;

    // diagonal: +++OOO, ++OO, +O, OOO+++, OO++, O+
    // push:  {starting point, direction}
    // diagonal:  {starting point, direction, number of marbles to move with} always counts from start to the right of the direction it moves to.
    // [1,2,3].length * directions.length => 3 * 6 = 18 potential action for one marble.
    // 18 * 28 marbles at most = 504 actions to validate at most
    public boolean isValidAction(AbaloneAction action) {
        // in-line
        int loc = action.location;
        char firstMarble = getState(loc);
        if (firstMarble == EMPTY) {
            return false;
        }
        if (action.numberOfMarbles == 1) {
            int player = firstMarble == BLACK ? 1 : 0;
            int i = 0;
            String currentMove = "";
            while (i < LONGEST_PUSH) {
                currentMove += getState(loc);

                String[] validMoves = VALID_PUSHES[player][i];

                for (String validMove : validMoves) {
                    if (validMove.equals(currentMove)) {
                        return true;
                    }
                }

                if (getState(loc) == OUT_OF_BOARD) {
                    return false;
                }

                loc = LOCATION_LOOKUP_TABLE[loc][action.direction];
                i++;
            }
        } else {
            // side-move
            int[] friendDirections = SIDE_MOVE_DIRECTION[action.direction];
            if (getState(LOCATION_LOOKUP_TABLE[loc][friendDirections[0]]) == firstMarble) {
                return isValidSideMove(action, friendDirections[0]);
            } else if (getState(LOCATION_LOOKUP_TABLE[loc][friendDirections[1]]) == firstMarble) { // check second friend
                return isValidSideMove(action, friendDirections[1]);
            }
        }
        return false;
    }

    // 2, 0, 7, AbaloneGame.UP_RIGHT
    private boolean isValidSideMove(AbaloneAction action, int friendDirection) {
        int loc = action.location;
        char firstMarble = getState(loc);
        // marbles has to be in a straight line
        int thirdFriendLoc = LOCATION_LOOKUP_TABLE[LOCATION_LOOKUP_TABLE[loc][friendDirection]][friendDirection];
        if (action.numberOfMarbles == 3
                && firstMarble != getState(thirdFriendLoc)) {
            return false;
        }
        // check if the blocks they are moving to are empty
        byte moveInLoc = LOCATION_LOOKUP_TABLE[loc][action.direction];
        for (int i = 0; i < action.numberOfMarbles; i++) {
            if (isInvalidLocation(loc) || isInvalidLocation(moveInLoc)) {
                return false;
            }
            if (EMPTY != getState(moveInLoc)) {
                return false;
            }
            moveInLoc = LOCATION_LOOKUP_TABLE[moveInLoc][friendDirection];
            loc = LOCATION_LOOKUP_TABLE[loc][friendDirection];
        }
        return true;
    }

    public boolean isTerminal(char[] state) {
        char b = 14;
        char w = 14;
        for (char piece : state) {
            switch (piece) {
                case BLACK:
                    b--;
                    break;
                case WHITE:
                    w--;
                    break;
            }
        }
        return b <= 8 || w <= 8;
    }

    @Override
    public char[] makeStateCopy(char[] state) {
        char[] copy = new char[state.length];
        System.arraycopy(state, 0, copy, 0, state.length);
        return copy;
    }

    // TODO
    public AbaloneGame execute(AbaloneAction action) {

        return new AbaloneGame();
    }

    public static final byte[][] LINEAR_LOCATION = new byte[][]{
            {0, 1, 2, 3, 4},
            {5, 6, 7, 8, 9, 10},
            {11, 12, 13, 14, 15, 16, 17},
            {18, 19, 20, 21, 22, 23, 24, 25},
            {26, 27, 28, 29, 30, 31, 32, 33, 34},
            {35, 36, 37, 38, 39, 40, 41, 42},
            {43, 44, 45, 46, 47, 48, 49},
            {50, 51, 52, 53, 54, 55},
            {56, 57, 58, 59, 60},
    };

    private char getState(int loc) {
        if (isInvalidLocation(loc)) {
            return OUT_OF_BOARD;
        }
        return state[loc];
    }

    private boolean isInvalidLocation(int loc) {
        return loc < 0 || loc >= 61;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Arrays.toString(state));
        return sb.toString();
    }
}
