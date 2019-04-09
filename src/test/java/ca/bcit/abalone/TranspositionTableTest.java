package ca.bcit.abalone;

import ca.bcit.abalone.ai.TranspositionTable;
import ca.bcit.abalone.game.AbaloneGame;
import org.junit.Test;

public class TranspositionTableTest {
    @Test
    public void testCollision() {
        Timer.time(() -> {
            AbaloneGame game = new AbaloneGame(new AbaloneGame.State(AbaloneGame.BELGIAN_DAISY_INITIAL_STATE, 1), -1);
            Benchmark benchmark = new Benchmark();
            benchmark.benchmark(game, 4);
//            game = game.result(game.actions()[0]);
//            benchmark.benchmark(game, 4);
//            game = game.result(game.actions()[0]);
//            benchmark.benchmark(game, 4);
            System.out.println("Capacity: " + benchmark.table.getCapacity());
//            System.out.println("Size: " + benchmark.table.getSize());
//            System.out.println("Hit: " + benchmark.table.getHit());
//            System.out.println("Collisions: " + benchmark.table.getCollision());
        });
    }

    public static class Benchmark {

        TranspositionTable table = new TranspositionTable(23);

        public void benchmark(AbaloneGame game, int level) {
            if (level == 0) {
                return;
            }
            for (AbaloneGame.Action a : game.actions()) {
                AbaloneGame next = game.result(a);
                table.put(next.zobristKey(), new TranspositionTable.History(next.zobristKey(), 1, 1));
                benchmark(next, level - 1);
            }
        }

    }
}
