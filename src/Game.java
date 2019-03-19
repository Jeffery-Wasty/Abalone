
public abstract class Game<P, S, A> {

    public boolean isTerminal;
    public A[] validActions;
    public S state;
    public P player;
    public int utility;

    public Game(final S state) {
        this.state = makeStateCopy(state);
    }

    protected void init() {
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

    public abstract S makeStateCopy(S state);

    public abstract Game<P, S, A> result(A action);

    protected abstract A[] actions(S state);

    protected abstract P getPlayer(S state);

    protected abstract boolean isTerminal(S state);

    protected abstract int getUtility(S state);

}