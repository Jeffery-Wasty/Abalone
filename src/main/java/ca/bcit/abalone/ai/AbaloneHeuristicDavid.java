package ca.bcit.abalone.ai;

import ca.bcit.abalone.game.AbaloneGame;

public class AbaloneHeuristicDavid {

    public static final int[] POSITION_WEIGHT_MAP = new int[]{
            1, 1, 1, 1, 1,
            1, 5, 5, 5, 5, 1,
            1, 5, 8, 8, 8, 5, 1,
            1, 5, 8, 10, 10, 8, 5, 1,
            1, 5, 8, 10, 13, 10, 8, 5, 1,
            1, 5, 8, 10, 10, 8, 5, 1,
            1, 5, 8, 8, 8, 5, 1,
            1, 5, 5, 5, 5, 1,
            1, 1, 1, 1, 1,
    };

    public static HeuristicCalculator<AbaloneGame> positionWeightedHeuristicDavid = (game, rootGame, info) -> {
        if (game.isTerminal()) {
            if (game.getUtility() > 0) {
                return Short.MAX_VALUE;
            } else if (game.getUtility() < 0) {
                return Short.MIN_VALUE;
            } else {
                return 0;
            }
        }
        int heuristic = 0;
        char[] state = game.state.getBoard();
        for (int i = 0; i < state.length; i++) {
            char marble = state[i];
            switch (marble) {
                case AbaloneGame.BLACK:
                    heuristic += heuristicValueDavid(state, i);
                    heuristic += 100;
                    break;
                case AbaloneGame.WHITE:
                    heuristic -= heuristicValueDavid(state, i);
                    heuristic -= 100;
                    break;
            }
        }
        return heuristic;
    };

    public static int heuristicValueDavid(char[] state, int i) {
        int neighbour = 0;
        int neighbourHorizontal = 0;
        int neighbourLeftToRightDiagonal = 0;
        int neighbourRightToLeftDiagonal = 0;
        int h = POSITION_WEIGHT_MAP[i] * 10;
        byte[] position = AbaloneGame.LOCATION_LOOKUP_TABLE[i];

        for(int j = 0; j < position.length; j++){
            switch(j){
                case 0:
                    if (position[j] != -1 && state[j] == state[i]) {
                        neighbourHorizontal++;
                        neighbour++;
                        break;
                    }
                case 1:
                    if (position[j] != -1 && state[j] == state[i]) {
                        neighbourLeftToRightDiagonal++;
                        neighbour++;
                        break;
                    }
                case 2:
                    if (position[j] != -1 && state[j] == state[i]) {
                        neighbourRightToLeftDiagonal++;
                        neighbour++;
                        break;
                    }
                case 3:
                    if (position[j] != -1 && state[j] == state[i]) {
                        neighbourHorizontal++;
                        neighbour++;
                        break;
                    }
                case 4:
                    if (position[j] != -1 && state[j] == state[i]) {
                        neighbourLeftToRightDiagonal++;
                        neighbour++;
                        break;
                    }
                case 5:
                    if (position[j] != -1 && state[j] == state[i]) {
                        neighbourRightToLeftDiagonal++;
                        neighbour++;
                        break;

                    }
            }
        }

        if(neighbourHorizontal == 2){
            h++;
        };
        if(neighbourLeftToRightDiagonal == 2){
            h++;
        };
        if(neighbourRightToLeftDiagonal == 2){
            h++;
        }
        if(neighbour == 6){
            h+=3;
        }
        return h;
    }

    public static void main(String[] args) {
        AbaloneGame game = new AbaloneGame(new AbaloneGame.State(AbaloneGame.BELGIAN_DAISY_INITIAL_STATE, 1), -1);
//        System.out.println(positionWeightedHeuristicDavid.getHeuristic(game, game));
    }

}