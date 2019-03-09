package ca.bcit.abalone.game;

import java.util.ArrayList;
import java.util.Arrays;

public class AbaloneGame extends Game<char[][], AbaloneAction> {

    public static final char BLACK = '@';
    public static final char WHITE = 'O';
    public static final char EMPTY = '+';
    public static final char OUT_OF_BOARD = '!';

    /**
     * I O O O O O            0-4
     * H O O O O O O          1-3
     * G + + O O O + +        2-2
     * F + + + + + + + +      3-1
     * E + + + + + + + + +    4-0
     * D + + + + + + + + 9    5-1
     * C + + @ @ @ + + 8      6-2
     * B @ @ @ @ @ @ 7        7-3
     * A @ @ @ @ @ 6          8-4
     * ..1 2 3 4 5
     */
    public static final char[][] INITIAL_STATE = new char[][]{
            {'O', 'O', 'O', 'O', 'O',},
            {'O', 'O', 'O', 'O', 'O', 'O',},
            {'+', '+', 'O', 'O', 'O', '+', '+',},
            {'+', '+', '+', '+', '+', '+', '+', '+',},
            {'+', '+', '+', '+', '+', '+', '+', '+', '+',},
            {'+', '+', '+', '+', '+', '+', '+', '+',},
            {'+', '+', '@', '@', '@', '+', '+',},
            {'@', '@', '@', '@', '@', '@',},
            {'@', '@', '@', '@', '@',},
    };

    public AbaloneGame() {
        super(AbaloneGame.INITIAL_STATE);
    }

    public AbaloneGame(char[][] state) {
        super(state);
    }

    private static final int[][][] MOVE_IN_POSITION = new int[][][]{
            {
                    {-1, 0}, // LEFT
                    {-1, -1}, // UP_LEFT,
                    {0, -1}, // UP_RIGHT
                    {1, 0},  // RIGHT
                    {1, 1},   // DOWN_RIGHT
                    {0, 1}  // DOWN_LEFT
            },
            {
                    {-1, 0}, // LEFT
                    {0, -1}, // UP_LEFT,
                    {1, -1}, // UP_RIGHT
                    {1, 0},  // RIGHT
                    {0, 1},   // DOWN_RIGHT
                    {-1, 1}  // DOWN_LEFT
            },
    };

    private static int[][] getTransitionMatrix(int y, int direction) {
        if (y < 4) {
            return MOVE_IN_POSITION[0];
        } else if (y > 4) {
            return MOVE_IN_POSITION[1];
        } else {
            return direction < 3 ? MOVE_IN_POSITION[0] : MOVE_IN_POSITION[1];
        }
    }

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
    public AbaloneAction[] actions(char[][] state) {
        ArrayList<AbaloneAction> validActions = new ArrayList<>();
        for (int r = 0; r < state.length; r++) {
            for (int c = 0; c < state[r].length; c++) {
                for (int i = 1; i <= 3; i++) {
                    for (int j = 0; j < 6; j++) {
                        AbaloneAction action = new AbaloneAction(i, c, r, j);
                        if (isValidAction(action)) {
                            validActions.add(action);
                        }
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
    // ^^ AHHHH, it's wrong
    // [1,2,3].length * directions.length => 3 * 6 = 18 potential action for one marble.
    // 18 * 28 marbles at most = 504 actions to validate at most
    public boolean isValidAction(AbaloneAction action) {
        // in-line
        int x = action.x;
        int y = action.y;
        char firstMarble = getState(x, y);
        if (firstMarble == EMPTY) {
            return false;
        }
        int[][] transMatrix = getTransitionMatrix(y, action.direction);
        if (action.numberOfMarbles == 1) {
            int player = firstMarble == BLACK ? 1 : 0;
            int i = 0;
            String currentMove = "";
            while (i < LONGEST_PUSH) {
                currentMove += getState(x, y);

                String[] validMoves = VALID_PUSHES[player][i];

                for (String validMove : validMoves) {
                    if (validMove.equals(currentMove)) {
                        return true;
                    }
                }
                x += transMatrix[action.direction][0];
                y += transMatrix[action.direction][1];
                i++;
            }
        } else {
            // side-move
            int[] friendDirections = SIDE_MOVE_DIRECTION[action.direction];
            char friend = getState(x + transMatrix[friendDirections[0]][0], y + transMatrix[friendDirections[0]][1]);
            if (friend == firstMarble) {
                return isValidSideMove(action, transMatrix[friendDirections[0]]);
            } else { // check second friend
                if (getState(x + transMatrix[friendDirections[1]][0], y + transMatrix[friendDirections[1]][1]) == firstMarble) {
                    return isValidSideMove(action, transMatrix[friendDirections[1]]);
                }
            }
        }
        return false;
    }

    // 2, 0, 7, AbaloneGame.UP_RIGHT
    private boolean isValidSideMove(AbaloneAction action, int[] friendDirection) {
        int x = action.x;
        int y = action.y;
        char firstMarble = getState(x, y);
        // marbles has to be in a straight line
        int thirdFriendX = x + 2 * friendDirection[0];
        int thirdFriendY = y + 2 * friendDirection[1];
        if (action.numberOfMarbles == 3
                && firstMarble != getState(thirdFriendX, thirdFriendY)) {
            return false;
        }
        // check if the blocks they are moving to are empty
        int[][] transMatrix = getTransitionMatrix(y, action.direction);
        int moveInX = x + transMatrix[action.direction][0];
        int moveInY = y + transMatrix[action.direction][1];
        for (int i = 0; i < action.numberOfMarbles; i++) {
            if (!isValidPoint(x, y) || !isValidPoint(moveInX, moveInY)) {
                return false;
            }
            if (EMPTY != getState(moveInX, moveInY)) {
                return false;
            }
            moveInX += friendDirection[0];
            moveInY += friendDirection[1];
            x += friendDirection[0];
            y += friendDirection[1];
        }
        return true;
    }

    public boolean isTerminal(char[][] state) {
        char b = 14;
        char w = 14;
        for (char[] rowState : state) {
            for (char piece : rowState) {
                switch (piece) {
                    case BLACK:
                        b--;
                        break;
                    case WHITE:
                        w--;
                        break;
                }
            }
        }
        return b <= 8 || w <= 8;
    }

    @Override
    public char[][] makeStateCopy(char[][] state) {
        char[][] copy = new char[state.length][];
        for (int i = 0; i < state.length; i++) {
            char[] innerCopy = new char[state[i].length];
            for (int j = 0; j < state[i].length; j++) {
                innerCopy[j] = state[i][j];
            }
            copy[i] = innerCopy;
        }
        return copy;
    }

    // TODO
    public AbaloneGame execute(AbaloneAction action) {

        return new AbaloneGame();
    }

    private char getState(int col, int row) {
        if (!isValidPoint(col, row)) {
            return OUT_OF_BOARD;
        }
        return state[row][col];
    }

    private void setState(int col, int row, char pieceState) {
        state[row][col] = pieceState;
    }

    public static final int[] NUM_COLS_IN_ROW = new int[]{5, 6, 7, 8, 9, 8, 7, 6, 5};

    private boolean isValidPoint(int col, int row) {
        return col >= 0 && row >= 0 && row < 9 && col < NUM_COLS_IN_ROW[row];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < state.length; i++) {
            /*int spaces = 9 - NUM_COLS_IN_ROW[i];
            for (int j = 0; j < spaces; j++) {
                sb.append(' ');
            }*/
            sb.append(Arrays.toString(state[i]));
            sb.append('\n');
        }
        return sb.toString();
    }
}
