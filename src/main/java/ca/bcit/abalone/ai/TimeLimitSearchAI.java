package ca.bcit.abalone.ai;

import ca.bcit.abalone.game.Game;

public class TimeLimitSearchAI<P, S, A, G extends Game<P, S, A>> {

    private DepthLimitAlphaBetaAI<P, S, A, G> depthLimitAI;
    private A action;
    private int level;
    private long endTime;
    private int step;

    public TimeLimitSearchAI(HeuristicCalculator<G> heuristicCalculator, QuiescenceSearch<G> quiescenceSearch) {
        this.depthLimitAI = new DepthLimitAlphaBetaAI<>(heuristicCalculator, quiescenceSearch);
    }

    public A search(G game, long timeLimit, int initialLevel, int step) {
        depthLimitAI.setTerminate(false);
        action = null;
        level = initialLevel;
        endTime = System.currentTimeMillis() + timeLimit;
        this.step = step;

        return search(game);
    }

    private A search(G game) {
        if (System.currentTimeMillis() > endTime) {
            return null;
        }
        System.out.println("Start searching level " + level);
        long timeSpent = System.currentTimeMillis();
        A possibleAction = depthLimitAI.play(game, level, endTime - System.currentTimeMillis());
        if (depthLimitAI.isTerminate() || System.currentTimeMillis() > endTime) {
            System.out.println("Terminate current search, result discarded.");
            return null;
        }
        action = possibleAction;
        timeSpent = System.currentTimeMillis() - timeSpent;
        if (!depthLimitAI.isEarlyTermination()) {
            System.out.println("Reached terminal-test, end further searches.");
            depthLimitAI.setTerminate(true);
            return action;
        }
        if (System.currentTimeMillis() + Math.pow(10, step) * timeSpent > endTime) {
            System.out.println("Terminate search since the remaining time is less the spent time at this level");
            depthLimitAI.setTerminate(true);
            return action;
        }
        level += step;
        A nextAction = search(game);
        return nextAction != null ? nextAction : possibleAction;
    }

    public void setHeuristicCalculator(HeuristicCalculator<G> heuristicCalculator) {
        this.depthLimitAI.setHeuristicCalculator(heuristicCalculator);
    }

}
