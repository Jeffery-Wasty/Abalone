package ca.bcit.abalone.ai;

public class TranspositionTable {

    private final int capacity;
    private final int bitMask;
    private int size = 0;
    private int collision = 0;
    private int hit = 0;
    private final History[] transpositionTable;

    public TranspositionTable(int capacity) {
        this.capacity = 1 << capacity;
        bitMask = (1 << capacity) - 1;
        transpositionTable = new History[this.capacity];
    }

    public History get(long key) {
        int ndx = (int) (key & bitMask);
        History history = transpositionTable[ndx];
        if (history != null && history.zobristKey == key) {
            return history;
        }
        return null;
    }

    public void put(long key, History history) {
        int ndx = (int) (key & bitMask);
        History prev = transpositionTable[ndx];
        if (prev != null && prev.zobristKey == key && prev.depth > history.depth) {
            return;
        }
        if (prev == null) {
            size++;
        } else if (prev.zobristKey != key) {
            collision++;
        } else {
            hit++;
        }
        transpositionTable[ndx] = history;
    }

    public int getCapacity() {
        return capacity;
    }

    public int size() {
        return size;
    }

    public int getSize() {
        return size;
    }

    public int getCollision() {
        return collision;
    }

    public int getHit() {
        return hit;
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

}
