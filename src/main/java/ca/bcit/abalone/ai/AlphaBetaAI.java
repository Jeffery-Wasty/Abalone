package ca.bcit.abalone.ai;

import ca.bcit.abalone.game.Game;

public abstract class AlphaBetaAI<P, S, A> {

    abstract boolean isPlayerMax(P player);

    public A play(Game<P, S, A> game) {
        return isPlayerMax(game.player)
                ? maxAction(game, Integer.MIN_VALUE, Integer.MAX_VALUE)
                : minAction(game, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private A maxAction(Game<P, S, A> game, int alpha, int beta) {
        if (game.isTerminal) {
            return null;
        }
        A action = null;
        int value = Integer.MIN_VALUE;
        int s = (int) (Math.random() * game.validActions.length);
        for (A a : game.validActions) {
            int result = minValue(game.result(a), alpha, beta);
            if (result > value) {
                value = result;
                action = a;
            }
            alpha = Math.max(alpha, value);
        }
        return action;
    }

    private A minAction(Game<P, S, A> game, int alpha, int beta) {
        if (game.isTerminal) {
            return null;
        }
        int i = 0;
        A action = null;
        int value = Integer.MAX_VALUE;
        for (A a : game.validActions) {
            long time = System.currentTimeMillis();
            int result = maxValue(game.result(a), alpha, beta);
            if (result < value) {
                value = result;
                action = a;
            }
            beta = Math.min(beta, value);
            time = System.currentTimeMillis() - time;
            System.out.println(++i + "/" + game.validActions.length + " in " + time + "ms.");
        }
        return action;
    }

    private int maxValue(Game<P, S, A> game, int alpha, int beta) {
        if (game.isTerminal) {
            return game.utility;
        }
        int value = Integer.MIN_VALUE;
        for (A a : game.validActions) {
            int result = minValue(game.result(a), alpha, beta);
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

    private int minValue(Game<P, S, A> game, int alpha, int beta) {
        if (game.isTerminal) {
            return game.utility;
        }
        int value = Integer.MAX_VALUE;
        for (A a : game.validActions) {
            int result = maxValue(game.result(a), alpha, beta);
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
