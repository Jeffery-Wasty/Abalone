package ca.bcit.abalone.game;

public abstract class Game<P, S, A> {

    public final boolean isTerminal;
    public final A[] validActions;
    public final S state;
    public final P player;
    public final int utility;

    public Game(final S state) {
        this.state = makeStateCopy(state);
        this.isTerminal = isTerminal(state);
        this.player = getPlayer(state);
        if (this.isTerminal) {
            utility = getUtility(state);
            this.validActions = null;
        } else {
            this.validActions = actions(state);
            utility = 0;
        }
    }

    protected abstract S makeStateCopy(S state);

    protected abstract Game result(A action);

    protected abstract A[] actions(S state);

    protected abstract P getPlayer(S state);

    protected abstract boolean isTerminal(S state);

    protected abstract int getUtility(S state);

}
