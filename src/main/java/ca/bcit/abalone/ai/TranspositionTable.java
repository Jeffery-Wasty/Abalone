package ca.bcit.abalone.ai;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class TranspositionTable {

    private final int capacity;
    private final int bitMask;
    private final int numberOfQueues;
    private int size = 0;
    private int collision = 0;
    private int hit = 0;
    private final long[][] transpositionTable;
//    private final ArrayList<ArrayList<History>> queues;

    public TranspositionTable(int capacity, int numberOfQueues) {
        this.capacity = 1 << capacity;
        this.numberOfQueues = numberOfQueues;
        bitMask = (1 << capacity) - 1;
        transpositionTable = new long[this.capacity][];
//        this.queues = new ArrayList<>(numberOfQueues);
//        for (int i = 0; i < numberOfQueues; i++) {
//            this.queues.add(new ArrayList<>());
//        }
    }

    public long[] get(long key) {
        int ndx = (int) (key & bitMask);
        long[] history = transpositionTable[ndx];
        if (history != null && history[0] == key) {
            return history;
        }
        return null;
    }

    public void put(long[] history) {
        long key = history[0];
        int ndx = (int) (key & bitMask);
        long[] prev = transpositionTable[ndx];
        if (prev != null && prev[0] == key && prev[1] >= history[1]) {
            return;
        }
//        if (prev == null) {
////            size++;
////        } else if (prev[0] != key) {
////            collision++;
////        } else {
////            hit++;
////        }
        transpositionTable[ndx] = history;
    }

//    public void flush() {
//        for (ArrayList<History> queue : queues) {
//            for (History h : queue) {
//                this.put(h);
//            }
//            queue.clear();
//        }
//    }
//
//    public void queue(int queueId, History history) {
//        queues.get(queueId).add(history);
//    }

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

        public final long zobristKey; // 0
        public final int depth; // 1
        public final int value; // 2

        public History(long zobristKey, int depth, int value) {
            this.zobristKey = zobristKey;
            this.depth = depth;
            this.value = value;
        }

    }

    public static void fromFile() throws IOException {
        RandomAccessFile f = new RandomAccessFile("table.txt", "r");
        MappedByteBuffer mappedByteBuffer = f.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, 5);
        for (int i = 0; i < 5; i++) {
            System.out.println(mappedByteBuffer.get(i));
        }
    }

    public static void main(String[] args) throws IOException {
        fromFile();
    }

}
