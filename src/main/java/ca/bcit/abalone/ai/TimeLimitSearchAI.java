package ca.bcit.abalone.ai;

import ca.bcit.abalone.game.Game;

import java.util.concurrent.TimeUnit;

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

        Thread thread = new Thread(() -> {
            searchNextLevel(game);
        });
        thread.start();

        long prevCheckTime = System.currentTimeMillis();

        while (!depthLimitAI.isTerminate() && System.currentTimeMillis() < endTime) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                long waitTime = System.currentTimeMillis() - prevCheckTime;
                if (waitTime > 110) {
                    System.err.println("Waited " + waitTime + "ms");
                }
                prevCheckTime = System.currentTimeMillis();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        thread.interrupt();
        depthLimitAI.setTerminate(true);

        return action;
    }

    private void searchNextLevel(G game) {
        if (System.currentTimeMillis() > endTime) {
            return;
        }
        System.out.println("Start searching level " + level);
        long timeSpent = System.currentTimeMillis();
        A possibleAction = depthLimitAI.play(game, level);
        if (depthLimitAI.isTerminate() || System.currentTimeMillis() > endTime) {
            System.out.println("Terminate current search, result discarded.");
            return;
        }
        action = possibleAction;
        timeSpent = System.currentTimeMillis() - timeSpent;
        System.out.println("level " + level + ": " + timeSpent + "ms");
        if (!depthLimitAI.isEarlyTermination()) {
            System.out.println("Reached terminal-test, end further searches.");
            depthLimitAI.setTerminate(true);
            return;
        }
        if (System.currentTimeMillis() + Math.pow(10, step) * timeSpent > endTime) {
            System.out.println("Terminate search since the remaining time is less the spent time at this level");
            depthLimitAI.setTerminate(true);
            return;
        }
        level += step;
        if (level > 4) {
            return;
        }
        searchNextLevel(game);
    }

    public void setHeuristicCalculator(HeuristicCalculator<G> heuristicCalculator) {
        this.depthLimitAI.setHeuristicCalculator(heuristicCalculator);
    }

}
