import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AbaloneGame extends Game<Character, AbaloneGame.State, AbaloneGame.Action> {

    public static final char BLACK = '@';
    public static final char WHITE = 'O';
    public static final char EMPTY = '+';
    public static final char OUT_OF_BOARD = '!';

    public static char[] INITIAL_STATE = new char[]{
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

    private final int turnLimit;

    public AbaloneGame() {
        this(new AbaloneGame.State(AbaloneGame.INITIAL_STATE, 1), -1);
    }

    public AbaloneGame(AbaloneGame.State state, int turnLimit) {
        super(state);
        this.turnLimit = turnLimit;
        super.init();
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
    public static final byte[][] SIDE_MOVE_DIRECTION = new byte[][]{
            {AbaloneAction.UP_LEFT, AbaloneAction.UP_RIGHT}, // LEFT
            {AbaloneAction.UP_RIGHT, AbaloneAction.RIGHT},// UP_LEFT
            {AbaloneAction.RIGHT, AbaloneAction.DOWN_RIGHT}, // UP_RIGHT,
            {AbaloneAction.DOWN_RIGHT, AbaloneAction.DOWN_LEFT}, // RIGHT
            {AbaloneAction.DOWN_LEFT, AbaloneAction.LEFT},  // DOWN_RIGHT
            {AbaloneAction.LEFT, AbaloneAction.UP_LEFT}   // DOWN_LEFT
    };
    
    public void standardLayout() {
    	INITIAL_STATE = new char[]{
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
    }
    
    public void germanDaisy() {
    	INITIAL_STATE = new char[]{
    			'+', '+', '+', '+', '+',
    			'O', 'O','+', '+','@', '@',
    			'O', 'O', 'O','+','@', '@', '@',
    			'+','O', 'O','+', '+','@', '@','+',
    			'+', '+', '+', '+', '+', '+', '+', '+', '+',
    			'+','@', '@','+','+','O', 'O','+',
    			'@', '@', '@','+','O', 'O', 'O',
    			'@', '@','+','+','O', 'O',
    			'+', '+', '+', '+', '+',
    	};
    }
    
    public void belgianDaisy() {
    	INITIAL_STATE = new char[]{
    			'O', 'O','+', '@', '@',
    			'O', 'O', 'O','@', '@', '@',
    			'+','O', 'O','+','@', '@','+',
    			'+', '+', '+', '+', '+', '+', '+', '+', 
    			'+', '+', '+', '+', '+', '+', '+', '+', '+',
    			'+', '+', '+', '+', '+', '+', '+', '+',
    			'+','@', '@','+','O', 'O','+',
    			'@', '@', '@','O', 'O', 'O',
    			'@', '@','+','O', 'O',
    			
    			
    			
    	};
    }
    // Enumerate all the 6 directions for each marbles on the board
    public AbaloneGame.Action[] actions(AbaloneGame.State state) {
        ArrayList<AbaloneGame.Action> validActions = new ArrayList<>();
        for (int loc = 0; loc < state.board.length; loc++) {
            for (int i = 1; i <= 3; i++) {
                for (int j = 0; j < 6; j++) {
                    AbaloneAction action = new AbaloneAction(i, loc, j);
                    AbaloneGame.Action gameAction = isValidAction(action);
                    if (gameAction != null) {
                        validActions.add(gameAction);
                    }
                }
            }
        }
        return validActions.toArray(new AbaloneGame.Action[0]);
    }

    @Override
    protected Character getPlayer(State state) {
        return state.turn % 2 == 1 ? BLACK : WHITE;
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

    public static class State {
        private char[] board;
        private byte turn;

        public State(char[] board, int turn) {
            this.board = board;
            this.turn = (byte) turn;
        }
    }

    public class Action {
        private final byte[][] newPieces;

        private Action(byte[][] newPieces) {
            this.newPieces = newPieces;
        }

        private Action(List<byte[]> gameAction) {
            newPieces = gameAction.toArray(new byte[0][0]);
        }

        @Override
        public String toString() {
            return "Action{" +
                    "newPieces=" + Arrays.deepToString(newPieces) +
                    '}';
        }
    }

    // diagonal: +++OOO, ++OO, +O, OOO+++, OO++, O+
    // push:  {starting point, direction}
    // diagonal:  {starting point, direction, number of marbles to move with} always counts from start to the right of the direction it moves to.
    // [1,2,3].length * directions.length => 3 * 6 = 18 potential action for one marble.
    // 18 * 28 marbles at most = 504 actions to validate at most
    public Action isValidAction(AbaloneAction action) {
        // in-line
        byte loc = action.location;
        char firstMarble = getState(loc);
        if (firstMarble != player) {
            return null;
        }
        if (action.numberOfMarbles == 1) {
            int player = firstMarble == BLACK ? 1 : 0;
            int i = 0;
            String currentMove = "";
            List<byte[]> gameAction = new ArrayList<>();
            gameAction.add(new byte[]{loc, EMPTY});

            while (i < LONGEST_PUSH) {
                char piece = getState(loc);
                currentMove += piece;

                String[] validMoves = VALID_PUSHES[player][i];

                if (piece != OUT_OF_BOARD && piece != EMPTY) {
                    loc = LOCATION_LOOKUP_TABLE[loc][action.direction];
                    if (!isInvalidLocation(loc)) {
                        gameAction.add(new byte[]{loc, (byte) piece});
                    }
                }

                for (String validMove : validMoves) {
                    if (validMove.equals(currentMove)) {
                        return new Action(gameAction);
                    }
                }

                if (piece == OUT_OF_BOARD) {
                    return null;
                }

                i++;
            }
        } else {
            // side-move
            byte[] friendDirections = SIDE_MOVE_DIRECTION[action.direction];
            if (getState(LOCATION_LOOKUP_TABLE[loc][friendDirections[0]]) == firstMarble) {
                return isValidSideMove(action, friendDirections[0]);
            } else if (getState(LOCATION_LOOKUP_TABLE[loc][friendDirections[1]]) == firstMarble) { // check second friend
                return isValidSideMove(action, friendDirections[1]);
            }
        }
        return null;
    }

    // 2, 0, 7, AbaloneGame.UP_RIGHT
    private Action isValidSideMove(AbaloneAction action, int friendDirection) {
        byte loc = action.location;
        char firstMarble = getState(loc);
        // marbles has to be in a straight line
        byte thirdFriendLoc = LOCATION_LOOKUP_TABLE[LOCATION_LOOKUP_TABLE[loc][friendDirection]][friendDirection];
        if (action.numberOfMarbles == 3
                && firstMarble != getState(thirdFriendLoc)) {
            return null;
        }

        List<byte[]> gameAction = new ArrayList<>();
        // check if the blocks they are moving to are empty
        byte moveInLoc = LOCATION_LOOKUP_TABLE[loc][action.direction];
        for (int i = 0; i < action.numberOfMarbles; i++) {
            if (isInvalidLocation(loc) || isInvalidLocation(moveInLoc)) {
                return null;
            }
            if (EMPTY != getState(moveInLoc)) {
                return null;
            }
            gameAction.add(new byte[]{loc, EMPTY});
            gameAction.add(new byte[]{moveInLoc, (byte) getState(loc)});
            moveInLoc = LOCATION_LOOKUP_TABLE[moveInLoc][friendDirection];
            loc = LOCATION_LOOKUP_TABLE[loc][friendDirection];
        }
        return new Action(gameAction);
    }

    public boolean isTerminal(AbaloneGame.State state) {
        if (turnLimit > 0 && state.turn > turnLimit) {
            return true;
        }
        byte[] lost = getNumberOfLostPieces();
        return lost[0] >= 6 || lost[1] >= 6;
    }

    private byte[] pieceLost = null;

    private byte[] getNumberOfLostPieces() {
        if (pieceLost != null) {
            return pieceLost;
        }
        byte b = 0;
        byte w = 0;
        for (char piece : state.board) {
            switch (piece) {
                case BLACK:
                    b++;
                    break;
                case WHITE:
                    w++;
                    break;
            }
        }
        pieceLost = new byte[]{(byte) (14 - b), (byte) (14 - w)};
        return pieceLost;
    }

    @Override
    protected int getUtility(State state) {
        byte[] pieceLost = getNumberOfLostPieces();
        return pieceLost[1] - pieceLost[0];
    }

    @Override
    public AbaloneGame.State makeStateCopy(AbaloneGame.State state) {
        char[] copy = new char[state.board.length];
        System.arraycopy(state.board, 0, copy, 0, state.board.length);
        return new AbaloneGame.State(copy, state.turn);
    }

    public AbaloneGame result(AbaloneGame.Action action) {
        AbaloneGame.State nextState = makeStateCopy(state);

        for (byte[] piece : action.newPieces) {
            if (piece[0] == -1) {
                System.out.println(this);
                System.out.println(action);
            }
            nextState.board[piece[0]] = (char) piece[1];
        }

        nextState.turn += 1;
        return new AbaloneGame(nextState, turnLimit);
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

    private char getState(byte loc) {
        if (isInvalidLocation(loc)) {
            return OUT_OF_BOARD;
        }
        return state.board[loc];
    }

    private boolean isInvalidLocation(byte loc) {
        return loc < 0 || loc >= 61;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("turn: ");
        sb.append(state.turn);
        sb.append("\n");
        for (byte[] row : LINEAR_LOCATION) {
            for (int i = 0; i < 9 - row.length; i++) sb.append(' ');
            for (byte loc : row) {
                sb.append(getState(loc));
                sb.append(", ");
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}