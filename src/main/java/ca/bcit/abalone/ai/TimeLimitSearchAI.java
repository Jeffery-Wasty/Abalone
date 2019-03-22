package ca.bcit.abalone.ai;

import ca.bcit.abalone.game.Game;

public class TimeLimitSearchAI<P, S, A> {

    private DepthLimitAlphaBetaAI<P, S, A> depthLimitAI = new DepthLimitAlphaBetaAI<>();
    private A action;
    private int level;
    private long endTime;
    private int step;

    public A search(Game<P, S, A> game, long timeLimit, int initialLevel, int step) {
        depthLimitAI.setTerminate(false);
        action = null;
        level = initialLevel;
        endTime = System.currentTimeMillis() + timeLimit;
        this.step = step;

        Thread thread = new Thread(() -> {
            searchNextLevel(game);
        });
        thread.start();

        while (!depthLimitAI.isTerminate() && System.currentTimeMillis() < endTime) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        thread.interrupt();
        depthLimitAI.setTerminate(true);

        return action;
    }

    private void searchNextLevel(Game<P, S, A> game) {
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
        if (System.currentTimeMillis() + 2 * step * timeSpent > endTime) {
            System.out.println("Terminate search since the remaining time is less the spent time at this level");
            depthLimitAI.setTerminate(true);
            return;
        }
        level += step;
        searchNextLevel(game);
    }

}
