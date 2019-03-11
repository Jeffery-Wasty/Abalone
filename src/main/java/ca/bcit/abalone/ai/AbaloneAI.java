package ca.bcit.abalone.ai;

import ca.bcit.abalone.game.AbaloneGame;

public class AbaloneAI extends AlphaBetaAI<Character, AbaloneGame.State, AbaloneGame.Action> {

    @Override
    boolean isPlayerMax(Character player) {
        return player == AbaloneGame.BLACK;
    }

}
