package ca.bcit.abalone.ai;

import ca.bcit.abalone.game.AbaloneGame;

public class AbaloneHeuristic {

    public static final int[] POSITION_WEIGHT_MAP = new int[]{
            1, 2, 2, 2, 1,
            2, 4, 4, 4, 4, 2,
            2, 4, 7, 7, 7, 4, 2,
            2, 4, 7, 10, 10, 7, 4, 2,
            1, 4, 7, 10, 15, 10, 7, 4, 1,
            2, 4, 7, 10, 10, 7, 4, 2,
            2, 4, 7, 7, 7, 4, 2,
            2, 4, 4, 4, 4, 2,
            1, 2, 2, 2, 1,
    };

    public static final int[] POSITION_PUSH_MAP = new int[]{
            15, 10, 10, 10, 15,
            10, 7, 7, 7, 7, 10,
            10, 7, 3, 3, 3, 7, 10,
            10, 7, 3, 2, 2, 2, 7, 10,
            15, 7, 3, 2, 1, 2, 3, 7, 15,
            10, 7, 3, 2, 2, 3, 7, 10,
            10, 7, 3, 3, 3, 7, 10,
            10, 7, 7, 7, 7, 10,
            15, 10, 10, 10, 15,
    };


    private static int assessBoard(char[] board)
    {
        int pieceAdv = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == AbaloneGame.BLACK)
                pieceAdv++;
            else
                pieceAdv--;
        }

        return pieceAdv;
    }

    //
    //
    //
    //Assess the piece advantage on the board
    private static int assessBoard2(char[] board, char colour)
    {
        char oppColor;
        int pieceAdv = 0, oppOnOutside = 0, boardStateScore = 0;
        //looking for positions of opponents pieces
        if (colour == AbaloneGame.BLACK)
            oppColor = AbaloneGame.WHITE;
        else
            oppColor = AbaloneGame.BLACK;

        for (int i = 0; i < board.length; i++) {
            if (board[i] == AbaloneGame.BLACK)
                pieceAdv++;
            else
                pieceAdv--;

            //max for black
            //min for white
            if (board[i] == oppColor && oppColor == AbaloneGame.BLACK)
                oppOnOutside -= POSITION_PUSH_MAP[i];
            else if (board[i] == oppColor)
                oppOnOutside += POSITION_PUSH_MAP[i];
        }

        boardStateScore = (pieceAdv * 10000) + (oppOnOutside * 10);
        return boardStateScore;
    }

    //counts piece adjacency
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

    public static HeuristicCalculator<AbaloneGame> positionAndEnemyOutsideHeuristic = (game) -> {
        int heuristic = 0;
        char[] state = game.state.getBoard();

        int boardAdv = assessBoard(state);
        heuristic += (boardAdv * 10000);

        for (int i = 0; i < state.length; i++) {
            char marble = state[i];
            switch (marble) {
                case AbaloneGame.BLACK:
                    heuristic += (POSITION_WEIGHT_MAP[i] + 25);
                    heuristic += countSurrounding(i, AbaloneGame.BLACK) + 10;
                    break;
                case AbaloneGame.WHITE:
                    heuristic -= (POSITION_WEIGHT_MAP[i] + 25);
                    heuristic -= countSurrounding(i, AbaloneGame.WHITE) + 10;
                    break;
            }
        }
        return heuristic;
    };

    public static HeuristicCalculator<AbaloneGame> positionAndEnemyOutsideHeuristic2 = (game) -> {
        int heuristic = 0;
        char[] state = game.state.getBoard();

        int boardAdv = assessBoard2(state, game.getPlayer());
        heuristic += boardAdv;

        for (int i = 0; i < state.length; i++) {
            char marble = state[i];
            switch (marble) {
                case AbaloneGame.BLACK:
                    heuristic += (POSITION_WEIGHT_MAP[i] + 50);
                    heuristic += countSurrounding(i, AbaloneGame.BLACK) + 50;
                    break;
                case AbaloneGame.WHITE:
                    heuristic -= (POSITION_WEIGHT_MAP[i] + 50);
                    heuristic -= countSurrounding(i, AbaloneGame.WHITE) + 50;
                    break;
            }
        }
        return heuristic;
    };

    public static HeuristicCalculator<AbaloneGame> positionAndEnemyOutsideHeuristic2Point5 = (game) -> {
        int heuristic = 0;
        char[] state = game.state.getBoard();

        int boardAdv = assessBoard2(state, game.getPlayer());
        heuristic += boardAdv;

        for (int i = 0; i < state.length; i++) {
            char marble = state[i];
            switch (marble) {
                case AbaloneGame.BLACK:
                    heuristic += (POSITION_WEIGHT_MAP[i] + 10);
                    heuristic += countSurrounding(i, AbaloneGame.BLACK) + 50;
                    break;
                case AbaloneGame.WHITE:
                    heuristic -= (POSITION_WEIGHT_MAP[i] + 10);
                    heuristic -= countSurrounding(i, AbaloneGame.WHITE) + 50;
                    break;
            }
        }
        return heuristic;
    };

    public static void main(String[] args) {
        AbaloneGame game = new AbaloneGame(new AbaloneGame.State(AbaloneGame.BELGIAN_DAISY_INITIAL_STATE, 1), -1);
        //System.out.println(simplePositionWeightedHeuristic.getHeuristic(game));
    }

}
