package ca.bcit.abalone.ai;

import ca.bcit.abalone.game.Game;

public interface HeuristicCalculator<G extends Game> {

    int getHeuristic(G game, G rootGame, AbaloneHeuristicJason.AdditionalInfo info);

}
