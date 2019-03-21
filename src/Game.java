
public abstract class Game<P, S, A> {

    boolean isTerminal;
    private A[] validActions;
    S state;
    P player;
    private int utility;

    Game(final S state) {
        this.state = makeStateCopy(state);
    }

    void init() {
        this.isTerminal = isTerminal(state);
        this.player = getPlayer(state);
        if (this.isTerminal) {
            this.utility = getUtility(state);
            this.validActions = null;
        } else {
            this.validActions = actions(state);
            this.utility = 0;
        }
    }

    public abstract S makeStateCopy(S state);

    public abstract Game<P, S, A> result(A action);

    protected abstract A[] actions(S state);

    protected abstract P getPlayer(S state);

    protected abstract boolean isTerminal(S state);

    protected abstract int getUtility(S state);

    @SuppressWarnings("unused")
    public boolean isTerminal() {
        return isTerminal;
    }

    @SuppressWarnings("unused")
    public A[] getValidActions() {
        return validActions;
    }

    @SuppressWarnings("unused")
    public S getState() {
        return state;
    }

    @SuppressWarnings("unused")
    public P getPlayer() {
        return player;
    }

    @SuppressWarnings("unused")
    public int getUtility() {
        return utility;
    }
}