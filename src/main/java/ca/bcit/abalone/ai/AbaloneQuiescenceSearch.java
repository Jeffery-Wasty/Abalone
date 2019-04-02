package ca.bcit.abalone.ai;

import ca.bcit.abalone.game.AbaloneGame;

public class AbaloneQuiescenceSearch {

    public static final QuiescenceSearch<AbaloneGame> SIMPLE_QUIESCENCE = (root, leaf) -> {
        byte[] rootPieceLost = root.getNumberOfLostPieces();
        byte[] leafPieceLost = leaf.getNumberOfLostPieces();
        return rootPieceLost[0] != leafPieceLost[0] || rootPieceLost[1] != leafPieceLost[1];
//        return false;
//        return root.getUtility() != leaf.getUtility();
    };

}
