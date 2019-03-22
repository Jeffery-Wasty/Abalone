package ca.bcit.abalone.ai;

import ca.bcit.abalone.game.Game;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DepthLimitAlphaBetaAI<P, S, A> {

    private ExecutorService threadPoolExecutor;

    private int alpha;
    private int beta;
    private int value;
    private A action;

    private int maxLevel;

    private int numberOfDone;

    public synchronized A play(Game<P, S, A> game, int initialLevel, int step, long timeLimit) {
        this.maxLevel = initialLevel;

        long endTime = System.currentTimeMillis() + timeLimit - 1000;

        Thread thread = new Thread(() -> {
            while (System.currentTimeMillis() < endTime) {
                alpha = Integer.MIN_VALUE;
                beta = Integer.MAX_VALUE;
                numberOfDone = 0;
                threadPoolExecutor = Executors.newFixedThreadPool(8);
                maxLevel += step;
                if (game.isPlayerMax(game.getPlayer())) {
                    maxAction(game);
                } else {
                    minAction(game);
                }
            }
        });
        thread.start();

        while (System.currentTimeMillis() < endTime) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        thread.interrupt();

        return action;
    }

    private A maxAction(Game<P, S, A> game) {
        if (game.isTerminal()) {
            return null;
        }
        value = Integer.MIN_VALUE;
        for (A a : game.actions()) {
            threadPoolExecutor.execute(() -> {
                int result = minValue(game.result(a), 1);
                if (result > value) {
                    value = result;
                    action = a;
                }
                alpha = Math.max(alpha, value);
            });
        }
        threadPoolExecutor.shutdown();
        try {
            threadPoolExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return action;
    }

    private A minAction(Game<P, S, A> game) {
        if (game.isTerminal()) {
            return null;
        }
        value = Integer.MAX_VALUE;
        long time = System.currentTimeMillis();
        for (A a : game.actions()) {
            threadPoolExecutor.execute(() -> {
                int result = maxValue(game.result(a), 1);
                if (result < value) {
                    value = result;
                    action = a;
                }
                beta = Math.min(beta, value);
                System.out.println(++numberOfDone + "/" + game.actions().length);
            });
        }
        threadPoolExecutor.shutdown();
        try {
            threadPoolExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Search completed in " + time + " ms");
        return action;
    }

    private int maxValue(Game<P, S, A> game, int level) {
        if (level >= maxLevel || game.isTerminal()) {
            return game.getUtility();
        }
        int value = Integer.MIN_VALUE;
        for (A a : game.actions()) {
            int result = minValue(game.result(a), level + 1);
            if (result > value) {
                value = result;
            }
            if (value >= beta) {
                return value;
            }
            alpha = Math.max(alpha, value);
        }
        return value;
    }

    private int minValue(Game<P, S, A> game, int level) {
        if (level >= maxLevel || game.isTerminal()) {
            return game.getUtility();
        }
        int value = Integer.MAX_VALUE;
        for (A a : game.actions()) {
            int result = maxValue(game.result(a), level + 1);
            if (result < value) {
                value = result;
            }
            if (value <= alpha) {
                return value;
            }
            beta = Math.min(beta, value);
        }
        return value;
    }

}
