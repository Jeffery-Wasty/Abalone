package ca.bcit.abalone.ai;

import ca.bcit.abalone.game.Game;

import java.time.LocalDateTime;
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
    private QuiescenceSearch<G> quiescenceSearch;
    private int quiescenceDepth = -2;
    private int searchedCount = 0;
//    private TranspositionTable maxTranspositionTable = new TranspositionTable(28);
//    private TranspositionTable minTranspositionTable = new TranspositionTable(28);

    private G rootGame;

    public DepthLimitAlphaBetaAI(HeuristicCalculator<G> heuristicCalculator, QuiescenceSearch<G> quiescenceSearch) {
        this.heuristicCalculator = heuristicCalculator;
        this.quiescenceSearch = quiescenceSearch;
    }

    public A play(G game, int maxLevel) {
        searchedCount = 0;
        rootGame = game;
        this.maxLevel = maxLevel;
        threadPoolExecutor = Executors.newFixedThreadPool(4);
        earlyTermination = false;
        alpha = Integer.MIN_VALUE;
        beta = Integer.MAX_VALUE;
        action = null;

        return game.isPlayerMax(game.getPlayer())
                ? maxAction(game)
                : minAction(game);
    }

    private A maxAction(G game) {
        if (game.isTerminal()) {
            return null;
        }
        value = Integer.MIN_VALUE;
        long time = System.currentTimeMillis();
        for (A a : game.actions()) {
            threadPoolExecutor.execute(() -> {
                int result = minValue(game.result(a), alpha, beta, maxLevel);
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
            System.out.println(LocalDateTime.now() + " Level " + maxLevel + " Search Terminated");
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Search completed in " + time + " ms, " + searchedCount + " nodes, heuristic: " + value);
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
                int result = maxValue(game.result(a), alpha, beta, maxLevel);
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
            System.out.println(LocalDateTime.now() + " Level " + maxLevel + " Search Terminated ");
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Search completed in " + time + " ms, " + searchedCount + " nodes, heuristic: " + value);
        return action;
    }

    private int maxValue(G game, int alpha, int beta, int level) {
        if (terminate) {
            return 0;
        }
//        TranspositionTable transpositionTable = getTable(game.isPlayerMax(game.getPlayer()));
//        TranspositionTable.History h = transpositionTable.get(game.zobristKey());
//        if (h != null && h.depth >= level - quiescenceDepth) {
//            earlyTermination = true;
//            return h.value;
//        }
        if (
                level <= 0
//                level <= quiescenceDepth
//                || (level <= 0 && !quiescenceSearch.shouldSearchFurther(rootGame, game))
                        || game.isTerminal()) {
            searchedCount++;
            if (level <= 0) {
                earlyTermination = true;
            }
            return heuristicCalculator.getHeuristic(game, rootGame);
        }
        int value = Integer.MIN_VALUE;
        for (A a : game.actions()) {
            int result = minValue(game.result(a), alpha, beta, level - 1);
            if (result > value) {
                value = result;
            }
            if (value >= beta) {
                return value;
            }
            alpha = Math.max(alpha, value);
        }

//        transpositionTable.put(game.zobristKey(), new TranspositionTable.History(game.zobristKey(), level - quiescenceDepth, value));

        return value;
    }

    private int minValue(G game, int alpha, int beta, int level) {
        if (terminate) {
            return 0;
        }
//        TranspositionTable transpositionTable = getTable(game.isPlayerMax(game.getPlayer()));
//        TranspositionTable.History h = transpositionTable.get(game.zobristKey());
//        if (h != null && h.depth >= level - quiescenceDepth) {
//            earlyTermination = true;
//            return h.value;
//        }
        if (
                level <= 0
//                level <= quiescenceDepth
//                || (level <= 0 && !quiescenceSearch.shouldSearchFurther(rootGame, game))
                        || game.isTerminal()) {
            searchedCount++;
            if (level <= 0) {
                earlyTermination = true;
            }
            return heuristicCalculator.getHeuristic(game, rootGame);
        }
        int value = Integer.MAX_VALUE;
        for (A a : game.actions()) {
            int result = maxValue(game.result(a), alpha, beta, level - 1);
            if (result < value) {
                value = result;
            }
            if (value <= alpha) {
                return value;
            }
            beta = Math.min(beta, value);
        }

//        transpositionTable.put(game.zobristKey(), new TranspositionTable.History(game.zobristKey(), level - quiescenceDepth, value));

        return value;

    }

    public void setHeuristicCalculator(HeuristicCalculator<G> heuristicCalculator) {
        this.heuristicCalculator = heuristicCalculator;
    }

//    private TranspositionTable getTable(boolean isPlayerMax) {
//        if (isPlayerMax) {
//            return maxTranspositionTable;
//        } else {
//            return minTranspositionTable;
//        }
//    }

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