package ca.bcit.abalone.ai;

import java.util.ArrayList;

public class TranspositionTable {

    private final int capacity;
    private final int bitMask;
    private final int numberOfQueues;
    private int size = 0;
    private int collision = 0;
    private int hit = 0;
    private final History[] transpositionTable;
    private final ArrayList<ArrayList<History>> queues;

    public TranspositionTable(int capacity, int numberOfQueues) {
        this.capacity = 1 << capacity;
        this.numberOfQueues = numberOfQueues;
        bitMask = (1 << capacity) - 1;
        transpositionTable = new History[this.capacity];
        this.queues = new ArrayList<>(numberOfQueues);
        for (int i = 0; i < numberOfQueues; i++) {
            this.queues.add(new ArrayList<>());
        }
    }

    public History get(long key) {
        int ndx = (int) (key & bitMask);
        History history = transpositionTable[ndx];
        if (history != null && history.zobristKey == key) {
            return history;
        }
        return null;
    }

    public void put(History history) {
        long key = history.zobristKey;
        int ndx = (int) (key & bitMask);
        History prev = transpositionTable[ndx];
        if (prev != null && prev.zobristKey == key && prev.depth >= history.depth) {
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

    public void flush() {
        for (ArrayList<History> queue : queues) {
            for (History h : queue) {
                this.put(h);
            }
            queue.clear();
        }
    }

    public void queue(int queueId, History history) {
        queues.get(queueId).add(history);
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

        @Override
        public String toString() {
            return "History{" +
                    "zobristKey=" + zobristKey +
                    ", depth=" + depth +
                    ", value=" + value +
                    '}';
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("TranspositionTable{" +
                "capacity=" + capacity +
                ", size=" + size +
                ", collision=" + collision +
                ", hit=" + hit +
                ", transpositionTable=");
        for (History h : transpositionTable) {
            if (h != null) {
                sb.append(h.toString());
            }
        }
        sb.append('}');
        return sb.toString();
    }
}
