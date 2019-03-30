package ca.bcit.abalone.ai;

import ca.bcit.abalone.game.Game;

public abstract class Zobrist<P, S, A, G extends Game<P, S, A>> {

    protected long[][] zobrist;

    abstract public int hashCode(G game);

}
