package ca.bcit.abalone.ai;

import ca.bcit.abalone.game.Game;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DepthLimitAlphaBetaAI<P, S, A, G extends Game<P, S, A>> {

    private ExecutorService threadPoolExecutor;
    private int maxLevel;
    private boolean terminate;
    private int alpha;
    private int beta;
    private int value;
    private A action;
    private boolean earlyTermination;
    private HeuristicCalculator<G> heuristicCalculator;
    private HashMap<G, History> transpositionTable = new HashMap<>();
    private boolean isPlayerMax;

    public DepthLimitAlphaBetaAI(HeuristicCalculator<G> heuristicCalculator) {
        this.heuristicCalculator = heuristicCalculator;
    }

    public A play(G game, int maxLevel) {
        this.maxLevel = maxLevel;
        threadPoolExecutor = Executors.newFixedThreadPool(4);
        earlyTermination = false;
        alpha = Short.MIN_VALUE;
        beta = Short.MAX_VALUE;
        action = null;
        isPlayerMax = game.isPlayerMax(game.getPlayer());

        return game.isPlayerMax(game.getPlayer())
                ? maxAction(game)
                : minAction(game);
//        return minimaxAction(game);
    }

    private A maxAction(G game) {
        if (game.isTerminal()) {
            return null;
        }
        value = Integer.MIN_VALUE;
        long time = System.currentTimeMillis();
        for (A a : game.actions()) {
            threadPoolExecutor.execute(() -> {
                int result = minValue(game.result(a), alpha, beta, 1);
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
            System.out.println("Level " + maxLevel + " Search Terminated");
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Search completed in " + time + " ms, " + "heuristic: " + value);
        return action;
    }

    private A minAction(G game) {
        if (game.isTerminal()) {
            return null;
        }
        value = Integer.MAX_VALUE;
        long time = System.currentTimeMillis();
        for (A a : game.actions()) {
            threadPoolExecutor.execute(() -> {
                int result = maxValue(game.result(a), alpha, beta, 1);
                if (result < value) {
                    value = result;
                    action = a;
                }
                beta = Math.min(beta, value);
            });
        }
        threadPoolExecutor.shutdown();
        try {
            threadPoolExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            System.out.println("Level " + maxLevel + " Search Terminated");
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Search completed in " + time + " ms, " + "heuristic: " + value);
        return action;
    }

    private A minimaxAction(G game) {
        if (game.isTerminal()) {
            return null;
        }
        value = Short.MIN_VALUE;
        long time = System.currentTimeMillis();
        for (A a : game.actions()) {
            threadPoolExecutor.execute(() -> {
                int result = -minimaxValue(game.result(a), -beta, -alpha, 1);
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
            System.out.println("Level " + maxLevel + " Search Terminated");
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Search completed in " + time + " ms, " + "heuristic: " + value);
        return action;
    }

    private int maxValue(G game, int alpha, int beta, int level) {
        if (terminate || level >= maxLevel || game.isTerminal()) {
            if (level >= maxLevel) {
                earlyTermination = true;
            }
            return heuristicCalculator.getHeuristic(game);
        }
        int value = Integer.MIN_VALUE;
        for (A a : game.actions()) {
            int result = minValue(game.result(a), alpha, beta, level + 1);
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

    private int minValue(G game, int alpha, int beta, int level) {
        if (terminate || level >= maxLevel || game.isTerminal()) {
            if (level >= maxLevel) {
                earlyTermination = true;
            }
            return heuristicCalculator.getHeuristic(game);
        }
        int value = Integer.MAX_VALUE;
        for (A a : game.actions()) {
            int result = maxValue(game.result(a), alpha, beta, level + 1);
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

    private int minimaxValue(G game, int alpha, int beta, int level) {
        if (terminate || level >= maxLevel || game.isTerminal()) {
            if (level >= maxLevel) {
                earlyTermination = true;
            }
            int heuristic;
            if (isPlayerMax) {
                heuristic = heuristicCalculator.getHeuristic(game);
            } else {
                heuristic = -heuristicCalculator.getHeuristic(game);
            }
            if (isPlayerMax == game.isPlayerMax(game.getPlayer())) {
                return heuristic;
            }
            return -heuristic;
        }
        int value = Short.MIN_VALUE;
        for (A a : game.actions()) {
            int result = -minimaxValue(game.result(a), -beta, -alpha, level + 1);
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

    public boolean isEarlyTermination() {
        return earlyTermination;
    }

    public boolean isTerminate() {
        return terminate;
    }

    public void setTerminate(boolean terminate) {
        this.terminate = terminate;
    }

    public static class History {

        public final int depth;
        public final int flag;
        public final int value;

        public History(int depth, int flag, int value) {
            this.depth = depth;
            this.flag = flag;
            this.value = value;
        }
    }

}