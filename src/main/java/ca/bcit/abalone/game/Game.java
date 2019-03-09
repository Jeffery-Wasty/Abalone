package ca.bcit.abalone.game;

public abstract class Game<S, A> {

    public final boolean isTerminal;
    public final A[] validActions;

    public final S state;

    public Game(final S state) {
        this.state = makeStateCopy(state);
        this.isTerminal = this.isTerminal(state);
        this.validActions = this.actions(state);
    }

    abstract S makeStateCopy(S state);

    abstract Game execute(A action);

    abstract A[] actions(S state);

    abstract boolean isTerminal(S state);

}
