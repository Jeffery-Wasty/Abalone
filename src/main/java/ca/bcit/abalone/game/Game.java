package ca.bcit.abalone.game;

public abstract class Game<P, S, A> {

    public final S state;
    private boolean isTerminal;
    private A[] validActions;
    private P player;
    private int utility;

    private boolean assignedIsTerminal;
    private boolean assignedValidActions;
    private boolean assignedPlayer;
    private boolean assignedUtility;

    public Game(final S state) {
        this.state = makeStateCopy(state);
    }

    public abstract S makeStateCopy(S state);

    public abstract <T extends Game<P, S, A>> T result(A action);

    protected abstract A[] actions(S state);

    public abstract boolean isPlayerMax(P player);

    public A[] actions() {
        if (!assignedValidActions) {
            validActions = actions(state);
            assignedValidActions = true;
        }
        return validActions;
    }

    protected abstract P getPlayer(S state);

    public P getPlayer() {
        if (!assignedPlayer) {
            player = getPlayer(state);
            assignedPlayer = true;
        }
        return player;
    }

    protected abstract boolean isTerminal(S state);

    public boolean isTerminal() {
        if (!assignedIsTerminal) {
            isTerminal = isTerminal(state);
            assignedIsTerminal = true;
        }
        return isTerminal;
    }

    protected abstract int getUtility(S state);

    public int getUtility() {
        if (!assignedUtility) {
            utility = getUtility(state);
            assignedUtility = true;
        }
        return utility;
    }

    public abstract long zobristKey();

}
