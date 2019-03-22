package ca.bcit.abalone.ai;

import ca.bcit.abalone.game.Game;

public class DepthLimitAlphaBetaAI<P, S, A> {

    private int maxLevel;
    private boolean terminate;

    public A play(Game<P, S, A> game, int maxLevel) {
        this.maxLevel = maxLevel;

        return game.isPlayerMax(game.getPlayer())
                ? maxAction(game, Integer.MIN_VALUE, Integer.MAX_VALUE)
                : minAction(game, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private A maxAction(Game<P, S, A> game, int alpha, int beta) {
        if (game.isTerminal()) {
            return null;
        }
        int value = Integer.MIN_VALUE;
        A action = null;
        long time = System.currentTimeMillis();
        for (A a : game.actions()) {
            int result = minValue(game.result(a), alpha, beta, 1);
            if (result > value) {
                value = result;
                action = a;
            }
            alpha = Math.max(alpha, value);
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Search completed in " + time + " ms");
        return action;
    }

    private A minAction(Game<P, S, A> game, int alpha, int beta) {
        if (game.isTerminal()) {
            return null;
        }
        int value = Integer.MAX_VALUE;
        A action = null;
        long time = System.currentTimeMillis();
        for (A a : game.actions()) {
            int result = maxValue(game.result(a), alpha, beta, 1);
            if (result < value) {
                value = result;
                action = a;
            }
            beta = Math.min(beta, value);
//            System.out.println(++numberOfDone + "/" + game.actions().length);
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Search completed in " + time + " ms");
        return action;
    }

    private int maxValue(Game<P, S, A> game, int alpha, int beta, int level) {
        if (terminate || level >= maxLevel || game.isTerminal()) {
            return game.getUtility();
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

    private int minValue(Game<P, S, A> game, int alpha, int beta, int level) {
        if (terminate || level >= maxLevel || game.isTerminal()) {
            return game.getUtility();
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

    public boolean isTerminate() {
        return terminate;
    }

    public void setTerminate(boolean terminate) {
        this.terminate = terminate;
    }
}