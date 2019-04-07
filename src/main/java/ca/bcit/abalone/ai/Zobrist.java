package ca.bcit.abalone.ai;

import ca.bcit.abalone.game.Game;

public interface Zobrist<P, S, A, G extends Game<P, S, A>> {

    long hashCode(G game);

    long hashCode(G game, A action);

}
