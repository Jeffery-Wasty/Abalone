package ca.bcit.abalone.ai;

import ca.bcit.abalone.game.Game;

public class DepthLimitAlphaBetaAI<P, S, A> {

    public A play(Game<P, S, A> game, int maxLevel) {
        return game.isPlayerMax(game.getPlayer())
                ? maxAction(game, Integer.MIN_VALUE, Integer.MAX_VALUE, maxLevel)
                : minAction(game, Integer.MIN_VALUE, Integer.MAX_VALUE, maxLevel);
    }

    private A maxAction(Game<P, S, A> game, int alpha, int beta, int maxLevel) {
        if (game.isTerminal()) {
            return null;
        }
        A action = null;
        int value = Integer.MIN_VALUE;
        for (A a : game.actions()) {
            int result = minValue(game.result(a), alpha, beta, maxLevel, 1);
            if (result > value) {
                value = result;
                action = a;
            }
            alpha = Math.max(alpha, value);
        }
        return action;
    }

    private A minAction(Game<P, S, A> game, int alpha, int beta, int maxLevel) {
        if (game.isTerminal()) {
            return null;
        }
        int i = 0;
        A action = null;
        int value = Integer.MAX_VALUE;
        for (A a : game.actions()) {
            long time = System.currentTimeMillis();
            int result = maxValue(game.result(a), alpha, beta, maxLevel, 1);
            if (result < value) {
                value = result;
                action = a;
            }
            beta = Math.min(beta, value);
            time = System.currentTimeMillis() - time;
            System.out.println(++i + "/" + game.actions().length + " in " + time + "ms.");
        }
        return action;
    }

    private int maxValue(Game<P, S, A> game, int alpha, int beta, int maxLevel, int level) {
        if (level >= maxLevel || game.isTerminal()) {
            return game.getUtility();
        }
        int value = Integer.MIN_VALUE;
        for (A a : game.actions()) {
            int result = minValue(game.result(a), alpha, beta, maxLevel, level + 1);
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

    private int minValue(Game<P, S, A> game, int alpha, int beta, int maxLevel, int level) {
        if (level >= maxLevel || game.isTerminal()) {
            return game.getUtility();
        }
        int value = Integer.MAX_VALUE;
        for (A a : game.actions()) {
            int result = maxValue(game.result(a), alpha, beta, maxLevel, level + 1);
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
