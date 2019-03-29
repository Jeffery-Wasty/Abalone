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

    private HashMap<String, Integer> historyTable = new HashMap<>();

    public DepthLimitAlphaBetaAI(HeuristicCalculator<G> heuristicCalculator) {
        this.heuristicCalculator = heuristicCalculator;
    }

    public A play(G game, int maxLevel) {
        this.maxLevel = maxLevel;
        threadPoolExecutor = Executors.newFixedThreadPool(8);
        earlyTermination = false;
        alpha = Integer.MIN_VALUE;
        beta = Integer.MAX_VALUE;
        action = null;

        A a = game.isPlayerMax(game.getPlayer())
                ? maxAction(game)
                : minAction(game);
        if (!earlyTermination) {
            historyTable.putIfAbsent(game.serialize(), value);
        }
        return a;
    }



    private A maxAction(G game) {
        if (game.isTerminal()) {
            return null;
        }
        value = Integer.MIN_VALUE;
        long time = System.currentTimeMillis();
        for (A a : game.actions()) {
            threadPoolExecutor.execute(() -> {
                G g = game.result(a);
                Integer result = historyTable.get(g.serialize());
                if (result == null) {
                    result = minValue(g, alpha, beta, 1);
                } else {
                    System.out.println("used history");
                }
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
                G g = game.result(a);
                Integer result = historyTable.get(g.serialize());
                if (result == null) {
                    result = maxValue(g, alpha, beta, 1);
                } else {
                    System.out.println("used history");
                }
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

    private int maxValue(G game, int alpha, int beta, int level) {
        if (terminate || level >= maxLevel || game.isTerminal()) {
            if (level >= maxLevel) {
                earlyTermination = true;
            }
            return heuristicCalculator.getHeuristic(game);
        }
        int value = Integer.MIN_VALUE;
        for (A a : game.actions()) {
            G g = game.result(a);
            Integer result = historyTable.get(g.serialize());
            if (result == null) {
                result = minValue(g, alpha, beta, level + 1);
            } else {
                System.out.println("used history");
            }
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
            G g = game.result(a);
            Integer result = historyTable.get(g.serialize());
            if (result == null) {
                result = maxValue(g, alpha, beta, level + 1);
            } else {
                System.out.println("used history");
            }
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

    public void setHistoryTable(HashMap<String, Integer> historyTable) {
        this.historyTable = historyTable;
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

}