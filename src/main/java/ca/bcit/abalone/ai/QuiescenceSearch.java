package ca.bcit.abalone.ai;

import ca.bcit.abalone.game.Game;

public interface QuiescenceSearch<G extends Game> {

    boolean shouldSearchFurther(G root, G leaf);

}
