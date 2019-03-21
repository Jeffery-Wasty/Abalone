
public abstract class Game<P, S, A> {

    S state;
    P player;

    Game(final S state) {
        this.state = makeStateCopy(state);
    }

    void init() {
        boolean isTerminal = isTerminal(state);
        this.player = getPlayer(state);
        int utility;
        A[] validActions;
        if (isTerminal) {
            utility = getUtility(state);
            validActions = null;
        } else {
            validActions = actions(state);
            utility = 0;
        }
    }

    public abstract S makeStateCopy(S state);

    public abstract Game<P, S, A> result(A action);

    protected abstract A[] actions(S state);

    protected abstract P getPlayer(S state);

    protected abstract boolean isTerminal(S state);

    protected abstract int getUtility(S state);

}