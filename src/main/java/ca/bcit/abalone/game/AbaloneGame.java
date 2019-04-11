package ca.bcit.abalone.game;

import ca.bcit.abalone.ai.AbaloneZobrist;

import java.util.*;

public class AbaloneGame extends Game<Character, AbaloneGame.State, AbaloneGame.Action> {

    public static final char BLACK = '@';
    public static final char WHITE = 'O';
    public static final char EMPTY = '+';
    public static final char OUT_OF_BOARD = '!';

    public static final char[] STANDARD_INITIAL_STATE = new char[]{
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

    public static final char[] BELGIAN_DAISY_INITIAL_STATE = new char[]{
            'O', 'O', '+', '@', '@',
            'O', 'O', 'O', '@', '@', '@',
            '+', 'O', 'O', '+', '@', '@', '+',
            '+', '+', '+', '+', '+', '+', '+', '+',
            '+', '+', '+', '+', '+', '+', '+', '+', '+',
            '+', '+', '+', '+', '+', '+', '+', '+',
            '+', '@', '@', '+', 'O', 'O', '+',
            '@', '@', '@', 'O', 'O', 'O',
            '@', '@', '+', 'O', 'O',
    };

    public static final char[] GERMAN_DAISY_INITIAL_STATE = new char[]{
            '+', '+', '+', '+', '+',
            'O', 'O', '+', '+', '@', '@',
            'O', 'O', 'O', '+', '@', '@', '@',
            '+', 'O', 'O', '+', '+', '@', '@', '+',
            '+', '+', '+', '+', '+', '+', '+', '+', '+',
            '+', '@', '@', '+', '+', 'O', 'O', '+',
            '@', '@', '@', '+', 'O', 'O', 'O',
            '@', '@', '+', '+', 'O', 'O',
            '+', '+', '+', '+', '+',
    };

    private final int turnLimit;

    public AbaloneGame(AbaloneGame.State state, int turnLimit) {
        super(state);
        this.turnLimit = turnLimit;
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
            {35, 27, 28, 37, 44, 43,},
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

    /**
     * [first_check, second_check] = SIDE_MOVE_DIRECTION[MOVE_DIRECTION]
     */
    private static final byte[][] CLICK_DIRECTION_ALLOWED_SIDE_MOVE_DIRECTION = new byte[][]{
            {AbaloneAction.DOWN_RIGHT, AbaloneAction.DOWN_LEFT}, // LEFT
            {AbaloneAction.DOWN_LEFT, AbaloneAction.LEFT},// UP_LEFT
            {AbaloneAction.LEFT, AbaloneAction.UP_LEFT}, // UP_RIGHT,
            {AbaloneAction.UP_LEFT, AbaloneAction.UP_RIGHT}, // RIGHT
            {AbaloneAction.UP_RIGHT, AbaloneAction.RIGHT},  // DOWN_RIGHT
            {AbaloneAction.RIGHT, AbaloneAction.DOWN_RIGHT}   // DOWN_LEFT
    };

    // Enumerate all the 6 directions for each marbles on the board
    public AbaloneGame.Action[] actions(AbaloneGame.State state) {
        ArrayList<AbaloneGame.Action> validActions = new ArrayList<>();
        for (int loc = 0; loc < state.board.length; loc++) {
            addMarbleActions(loc, validActions);
        }
        if (state.turn == 1) {
            Collections.shuffle(validActions);
        }
        AbaloneGame.Action[] actions = validActions.toArray(new AbaloneGame.Action[0]);
        if (state.turn != 1) {
            Arrays.sort(actions, Action::compareTo);
        }
        return actions;
    }

    private void addMarbleActions(int loc, ArrayList<Action> validActions) {
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

    @Override
    protected Character getPlayer(State state) {
        return state.turn % 2 == 1 ? BLACK : WHITE;
    }

    public static final HashSet<String> VALID_INLINE_MOVES = new HashSet<String>() {{
        add("O+");
        add("OO+");
        add("OO@+");
        add("OO@!");
        add("OOO+");
        add("OOO@+");
        add("OOO@!");
        add("OOO@@+");
        add("OOO@@!");
        add("@+");
        add("@@+");
        add("@@O+");
        add("@@O!");
        add("@@@+");
        add("@@@O+");
        add("@@@O!");
        add("@@@OO+");
        add("@@@OO!");
    }};

    public static final HashSet<Long> VALID_INLINE_MOVES2 = new HashSet<Long>() {{
        add(726614080L);
        add(557862735L);
        add(185762533199L);
        add(2838351L);
        add(11072L);
        add(2834496L);
        add(558841920L);
        add(725634895L);
        add(142812860239L);
        add(47554956840783L);
        add(143063531584L);
        add(47619632021568L);
        add(725631040L);
        add(726617935L);
        add(36624515743808L);
        add(11087L);
        add(36559840563023L);
        add(186013204544L);
    }};

    public static final int LONGEST_PUSH = 6;

    public static class State {
        private char[] board;
        private int turn;

        public State(char[] board, int turn) {
            this.board = board;
            this.turn = turn;
        }

        public char[] getBoard() {
            return board;
        }

        public int getTurn() {
            return turn;
        }
    }

    public class Action implements Comparable<Action> {
        private final byte[][] newPieces;
        private int priority = -1;

        private Action(byte[][] newPieces) {
            this.newPieces = newPieces;
        }

        private Action(List<byte[]> gameAction) {
            this(gameAction.toArray(new byte[0][0]));
        }

        @Override
        public int compareTo(Action o) {
            return o.getPriority() - getPriority();
        }

        public int getPriority() {
            if (priority == -1) {
                priority = 0;
                for (byte[] p : newPieces) {
                    if (p[1] == getPlayer()) {
                        priority++;
                    }
                }
            }
            return priority;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Action{newPieces=[");
            for (byte[] marble : newPieces) {
                sb.append(marble[0]);
                sb.append("=");
                switch (marble[1]) {
                    case AbaloneGame.BLACK:
                        sb.append(2);
                        break;
                    case AbaloneGame.WHITE:
                        sb.append(1);
                        break;
                    case AbaloneGame.EMPTY:
                        sb.append(0);
                        break;
                }
                sb.append(", ");
            }
            sb.delete(sb.length() - 2, sb.length());
            sb.append("]}");
            return sb.toString();
        }

        public byte[][] getNewPieces() {
            return newPieces;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Action action = (Action) o;

            return Arrays.deepEquals(newPieces, action.newPieces);
        }

        @Override
        public int hashCode() {
            return Arrays.deepHashCode(newPieces);
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
        if (firstMarble != getPlayer()) {
            return null;
        }
        if (action.numberOfMarbles == 1) {
            int player = firstMarble == BLACK ? 1 : 0;
            int i = 0;
//            String currentMove = "";
            long currentMove = 0;
            List<byte[]> gameAction = new ArrayList<>(6);
            gameAction.add(new byte[]{loc, EMPTY});

            while (i < LONGEST_PUSH) {
                char piece = getState(loc);
//                currentMove += piece;
                currentMove |= ((long) piece) << (8 * i);

                if (piece != OUT_OF_BOARD && piece != EMPTY) {
                    loc = LOCATION_LOOKUP_TABLE[loc][action.direction];
                    if (!isInvalidLocation(loc)) {
                        gameAction.add(new byte[]{loc, (byte) piece});
                    }
                }

//                if (VALID_INLINE_MOVES.contains(currentMove)) {
//                    return new Action(gameAction);
//                }
                if (VALID_INLINE_MOVES2.contains(currentMove)) {
                    return new Action(gameAction);
                }

                if (piece == OUT_OF_BOARD || piece == EMPTY) {
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

        List<byte[]> gameAction = new ArrayList<>(6);
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

    public byte[] getNumberOfLostPieces() {
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

    @Override
    public AbaloneGame result(AbaloneGame.Action action) {
        AbaloneGame.State nextState = makeStateCopy(state);

        for (byte[] piece : action.newPieces) {
            nextState.board[piece[0]] = (char) piece[1];
        }

        nextState.turn += 1;

        return new AbaloneGame(nextState, turnLimit);
    }

    @Override
    public boolean isPlayerMax(Character player) {
        return player == AbaloneGame.BLACK;
    }

    public int[] isValidUIMove(List<Integer> clicks) {
        if (clicks.size() == 0 || clicks.size() > 4) {
            return new int[]{-1};
        }
        // the first click
        int firstIndex = clicks.get(0);
        char firstMarble = getState(firstIndex);
        // first click is not belong to current player, illegal
        if (firstMarble != getPlayer()) {
            return new int[]{-1};
        }
        // only one click and is not an empty location, stacking piece
        if (clicks.size() == 1) {
            return new int[]{0};
        }
        // the intermediate clicks (only 2 <= size <= 4 will reach here)
        int direction = -1;
        for (int i = 1; i < clicks.size() - 1; i++) {
            int currentIndex = clicks.get(i);
            int currentDirection = Utility.indexOf(LOCATION_LOOKUP_TABLE[clicks.get(i - 1)], (byte) currentIndex);
            // not a neighbour, illegal move
            if (currentDirection == -1) {
                return new int[]{-1};
            }
            if (direction == -1) {
                direction = currentDirection;
            } else if (direction != currentDirection) {
                // change in direction in a intermediate click, illegal move
                return new int[]{-1};
            }
            // clicking a different marble comparing to the first marble, illegal move
            if (getState(currentIndex) != firstMarble) {
                return new int[]{-1};
            }
        }
        // the last click
        int lastIndex = clicks.get(clicks.size() - 1);
        int lastDirection = Utility.indexOf(LOCATION_LOOKUP_TABLE[clicks.get(clicks.size() - 2)], (byte) lastIndex);
        char lastMarble = getState(lastIndex);
        // not a neighbour, illegal move
        if (lastDirection == -1) {
            return new int[]{-1};
        }
        // the last clicked marble is still the same color as the first marble
        if (lastMarble == firstMarble) {
            if (clicks.size() < 4) {
                // stacking marbles only if the number of stacked marbles will be <= 3
                return new int[]{0};
            } else {
                return new int[]{-1};
            }
        }
        if (direction != -1 && lastDirection != direction
                && Utility.indexOf(CLICK_DIRECTION_ALLOWED_SIDE_MOVE_DIRECTION[direction], (byte) lastDirection) == -1) {
            return new int[]{-1};
        }
        // if the last click will construct a valid action, returns the 3 action parameters
        int numberOfMarbles = lastDirection == direction ? 1 : clicks.size() - 1;
        // side-move that not moving to empty spaces => illegal
        if (numberOfMarbles != 1) {
            for (int i = 0; i < clicks.size() - 1; i++) {
                if (getState(LOCATION_LOOKUP_TABLE[clicks.get(i)][lastDirection]) != EMPTY) {
                    return new int[]{-1};
                }
            }
        }
        return new int[]{numberOfMarbles, firstIndex, lastDirection};
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

    public static final byte[][] ROTATION_ABALONE = new byte[][]{
            {
                    0, 1, 2, 3, 4,
                    5, 6, 7, 8, 9, 10,
                    11, 12, 13, 14, 15, 16, 17,
                    18, 19, 20, 21, 22, 23, 24, 25,
                    26, 27, 28, 29, 30, 31, 32, 33, 34,
                    35, 36, 37, 38, 39, 40, 41, 42,
                    43, 44, 45, 46, 47, 48, 49,
                    50, 51, 52, 53, 54, 55,
                    56, 57, 58, 59, 60
            },
            {
                    26, 18, 11, 5, 0,
                    35, 27, 19, 12, 6, 1,
                    43, 36, 28, 20, 13, 7, 2,
                    50, 44, 37, 29, 21, 14, 8, 3,
                    56, 51, 45, 38, 30, 22, 15, 9, 4,
                    57, 52, 46, 39, 31, 23, 16, 10,
                    58, 53, 47, 40, 32, 24, 17,
                    59, 54, 48, 41, 33, 25,
                    60, 55, 49, 42, 34
            },
            {
                    56, 50, 43, 35, 26,
                    57, 51, 44, 36, 27, 18,
                    58, 52, 45, 37, 28, 19, 11,
                    59, 53, 46, 38, 29, 20, 12, 5,
                    60, 54, 47, 39, 30, 21, 13, 6, 0,
                    55, 48, 40, 31, 22, 14, 7, 1,
                    49, 41, 32, 23, 15, 8, 2,
                    42, 33, 24, 16, 9, 3,
                    34, 25, 17, 10, 4
            },
            {
                    60, 59, 58, 57, 56,
                    55, 54, 53, 52, 51, 50,
                    49, 48, 47, 46, 45, 44, 43,
                    42, 41, 40, 39, 38, 37, 36, 35,
                    34, 33, 32, 31, 30, 29, 28, 27, 26,
                    25, 24, 23, 22, 21, 20, 19, 18,
                    17, 16, 15, 14, 13, 12, 11,
                    10, 9, 8, 7, 6, 5,
                    4, 3, 2, 1, 0
            },
            {
                    34, 42, 49, 55, 60,
                    25, 33, 41, 48, 54, 59,
                    17, 24, 32, 40, 47, 53, 58,
                    10, 16, 23, 31, 39, 46, 52, 57,
                    4, 9, 15, 22, 30, 38, 45, 51, 56,
                    3, 8, 14, 21, 29, 37, 44, 50,
                    2, 7, 13, 20, 28, 36, 43,
                    1, 6, 12, 19, 27, 35,
                    0, 5, 11, 18, 26
            },
            {
                    4, 10, 17, 25, 34,
                    3, 9, 16, 24, 33, 42,
                    2, 8, 15, 23, 32, 41, 49,
                    1, 7, 14, 22, 31, 40, 48, 55,
                    0, 6, 13, 21, 30, 39, 47, 54, 60,
                    5, 12, 20, 29, 38, 46, 53, 59,
                    11, 19, 28, 37, 45, 52, 58,
                    18, 27, 36, 44, 51, 57,
                    26, 35, 43, 50, 56
            },
            {
                    4, 3, 2, 1, 0,
                    10, 9, 8, 7, 6, 5,
                    17, 16, 15, 14, 13, 12, 11,
                    25, 24, 23, 22, 21, 20, 19, 18,
                    34, 33, 32, 31, 30, 29, 28, 27, 26,
                    42, 41, 40, 39, 38, 37, 36, 35,
                    49, 48, 47, 46, 45, 44, 43,
                    55, 54, 53, 52, 51, 50,
                    60, 59, 58, 57, 56
            },
            {
                    0, 5, 11, 18, 26,
                    1, 6, 12, 19, 27, 35,
                    2, 7, 13, 20, 28, 36, 43,
                    3, 8, 14, 21, 29, 37, 44, 50,
                    4, 9, 15, 22, 30, 38, 45, 51, 56,
                    10, 16, 23, 31, 39, 46, 52, 57,
                    17, 24, 32, 40, 47, 53, 58,
                    25, 33, 41, 48, 54, 59,
                    34, 42, 49, 55, 60
            },
            {
                    26, 35, 43, 50, 56,
                    18, 27, 36, 44, 51, 57,
                    11, 19, 28, 37, 45, 52, 58,
                    5, 12, 20, 29, 38, 46, 53, 59,
                    0, 6, 13, 21, 30, 39, 47, 54, 60,
                    1, 7, 14, 22, 31, 40, 48, 55,
                    2, 8, 15, 23, 32, 41, 49,
                    3, 9, 16, 24, 33, 42,
                    4, 10, 17, 25, 34
            },
            {
                    56, 57, 58, 59, 60,
                    50, 51, 52, 53, 54, 55,
                    43, 44, 45, 46, 47, 48, 49,
                    35, 36, 37, 38, 39, 40, 41, 42,
                    26, 27, 28, 29, 30, 31, 32, 33, 34,
                    18, 19, 20, 21, 22, 23, 24, 25,
                    11, 12, 13, 14, 15, 16, 17,
                    5, 6, 7, 8, 9, 10,
                    0, 1, 2, 3, 4
            },
            {
                    60, 55, 49, 42, 34,
                    59, 54, 48, 41, 33, 25,
                    58, 53, 47, 40, 32, 24, 17,
                    57, 52, 46, 39, 31, 23, 16, 10,
                    56, 51, 45, 38, 30, 22, 15, 9, 4,
                    50, 44, 37, 29, 21, 14, 8, 3,
                    43, 36, 28, 20, 13, 7, 2,
                    35, 27, 19, 12, 6, 1,
                    26, 18, 11, 5, 0
            },
            {
                    34, 25, 17, 10, 4,
                    42, 33, 24, 16, 9, 3,
                    49, 41, 32, 23, 15, 8, 2,
                    55, 48, 40, 31, 22, 14, 7, 1,
                    60, 54, 47, 39, 30, 21, 13, 6, 0,
                    59, 53, 46, 38, 29, 20, 12, 5,
                    58, 52, 45, 37, 28, 19, 11,
                    57, 51, 44, 36, 27, 18,
                    56, 50, 43, 35, 26
            }
    };

    private char getState(int loc) {
        if (isInvalidLocation(loc)) {
            return OUT_OF_BOARD;
        }
        return state.board[loc];
    }

    private boolean isInvalidLocation(int loc) {
        return loc < 0 || loc >= 61;
    }

    private Long hashcode = null;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbaloneGame that = (AbaloneGame) o;

        return hashCode() == that.hashCode() && hashcode.equals(that.hashcode);
    }

    @Override
    public int hashCode() {
        return Long.hashCode(zobristKey());
    }

    public long zobristKey() {
        if (hashcode == null) {
            hashcode = AbaloneZobrist.getInstance().hashCode(this);
        }
        return hashcode;
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

    public static void main(String[] args) {
        // check if nodes are actually ordered
        System.out.println(Arrays.toString(new AbaloneGame(new State(AbaloneGame.STANDARD_INITIAL_STATE, 3), -1).actions()));
        System.out.println(Arrays.toString(new AbaloneGame(new State(AbaloneGame.STANDARD_INITIAL_STATE, 2), -1).actions()));
    }

}