package ca.bcit.abalone.ai;

import java.util.Arrays;

import ca.bcit.abalone.game.AbaloneGame;

public class AbaloneHeuristicDavid {

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


    public static HeuristicCalculator<AbaloneGame> positionWeightedHeuristicDavid = (game) -> {
        int heuristic = 0;
        char[] state = game.state.getBoard();
        for (int i = 0; i < state.length; i++) {
            char marble = state[i];
            switch (marble) {
                case AbaloneGame.BLACK:
                    heuristic += heuristicValueDavid(state, i);
                    break;
                case AbaloneGame.WHITE:
                    heuristic -= heuristicValueDavid(state, i);
                    break;
            }
        }
        return heuristic;
    };

    public static int heuristicValueDavid(char[] state, int i) {
        //push enemy back add points -might be already done from white-=
        //knock out add points
        //check left and right for 3 in a row
        int neighbourHorizontal = 0;
        int neighbourLeftToRightDiagonal = 0;
        int neighbourRightToLeftDiagonal = 0;
        int h = POSITION_WEIGHT_MAP[i] * 10;
        byte[] position = AbaloneGame.LOCATION_LOOKUP_TABLE[i];
        System.out.println(Arrays.toString(position) + " here");

        for(int j = 0; j < position.length; j++){
            switch(j){
                case 0:
                if (position[j] != -1 && state[j] == state[i]) {
                    System.out.println("0 " + position[j] + " " + state[j]);
                    neighbourHorizontal++;
                    break;
                }  
                case 1:
                if (position[j] != -1 && state[j] == state[i]) {
                    System.out.println("1 " + position[j] + " " + state[j]);
                    neighbourLeftToRightDiagonal++;
                    break;
                } 
                case 2:
                if (position[j] != -1 && state[j] == state[i]) {
                    neighbourRightToLeftDiagonal++;
                    System.out.println("2 " + position[j] + " " + state[j]);
                    break;
                } 
                case 3:
                if (position[j] != -1 && state[j] == state[i]) {
                    neighbourHorizontal++;
                    System.out.println("3 " + position[j] + " " + state[j]);
                    break;
                } 
                case 4:
                if (position[j] != -1 && state[j] == state[i]) {
                    neighbourLeftToRightDiagonal++;
                    System.out.println("4 " + position[j] + " " + state[j]);
                    break;
                } 
                case 5:
                if (position[j] != -1 && state[j] == state[i]) {
                    neighbourRightToLeftDiagonal++;
                    System.out.println("5 " + position[j] + " " + state[j]);
                    break;
                } 
            }
        }

        return h;
    }

    public static int neighbourHeuristic(int numberOfNeighbours){
        int h = 0;


        return h;
    }

    public static void main(String[] args) {
        AbaloneGame game = new AbaloneGame(new AbaloneGame.State(AbaloneGame.BELGIAN_DAISY_INITIAL_STATE, 1), -1);
        System.out.println(positionWeightedHeuristicDavid.getHeuristic(game));
    }

}
