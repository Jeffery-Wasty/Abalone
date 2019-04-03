package ca.bcit.abalone.ai;

import ca.bcit.abalone.game.AbaloneGame;

public class TranspositionTable {

    private final int capacity;
    private int size = 0;
    private int collision = 0;
    private int hit = 0;
    private final History[] transpositionTable;

    public TranspositionTable(int capacity) {
        this.capacity = 1 << capacity;
        transpositionTable = new History[this.capacity];
    }

    public History get(long key) {
        int ndx = (int) (((key % capacity) + capacity) % capacity);
        History history = transpositionTable[ndx];
        if (history != null && history.zobristKey == key) {
            return history;
        }
        return null;
    }

    public void put(long key, History history) {
        int ndx = (int) (((key % capacity) + capacity) % capacity);
        History prev = transpositionTable[ndx];
        if (prev != null && prev.zobristKey == key && prev.depth > history.depth) {
            return;
        }
//        if (prev == null) {
//            size++;
//        } else if (prev.zobristKey != key) {
//            collision++;
//        } else {
//            hit++;
//        }
        transpositionTable[ndx] = history;
    }

    public int getCapacity() {
        return capacity;
    }

    public int size() {
        return size;
    }

    public static class History {

        public final long zobristKey;
        public final int depth;
        public final int value;

        public History(long zobristKey, int depth, int value) {
            this.zobristKey = zobristKey;
            this.depth = depth;
            this.value = value;
        }

    }

    public static class Benchmark {

        TranspositionTable table = new TranspositionTable(26);

        public void benchmark(AbaloneGame game, int level) {
            if (level == 0) {
                return;
            }
            for (AbaloneGame.Action a : game.actions()) {
                AbaloneGame next = game.result(a);
                table.put(next.zobristKey(), new History(next.zobristKey(), 1, 1));
                benchmark(next, level - 1);
            }
        }

    }

    public static void main(String[] args) {
        AbaloneGame game = new AbaloneGame(new AbaloneGame.State(AbaloneGame.BELGIAN_DAISY_INITIAL_STATE, 1), -1);
        Benchmark benchmark = new Benchmark();
        benchmark.benchmark(game, 4);
        System.out.println("Capacity: " + benchmark.table.capacity);
        System.out.println("Size: " + benchmark.table.size);
        System.out.println("Hit: " + benchmark.table.hit);
        System.out.println("Collisions: " + benchmark.table.collision);
    }

}
