package ca.bcit.abalone.ai;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class TranspositionTable {

    private final int capacity;
    private final int bitMask;
    private int size = 0;
    private int collision = 0;
    private int hit = 0;
    private final long[][] transpositionTable;

    public TranspositionTable(int capacity) {
        this.capacity = 1 << capacity;
        bitMask = (1 << capacity) - 1;
        transpositionTable = new long[this.capacity][];
    }

    public long[] get(long key) {
        int ndx = (int) (key & bitMask);
        long[] history = transpositionTable[ndx];
        if (history != null && history[0] == key) {
            hit++;
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
        if (prev == null) {
            size++;
        } else if (prev[0] != key) {
            collision++;
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

        public final long zobristKey; // 0
        public final int depth; // 1
        public final int value; // 2

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
        return "TranspositionTable{" +
                "capacity=" + capacity +
                ", size=" + size +
                ", collision=" + collision +
                ", hit=" + hit + '}';
    }

    public static TranspositionTable fromFile(File file, int tableSize) throws IOException {
        long size = file.length();
        TranspositionTable table = new TranspositionTable(tableSize);
        long time = System.currentTimeMillis();
        RandomAccessFile f = new RandomAccessFile(file, "r");
        MappedByteBuffer mappedByteBuffer = f.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, size);
        for (int i = 0; i < size; i += 24) {
            long key = mappedByteBuffer.getLong(i);
            long depth = mappedByteBuffer.getLong(i + 8);
            long value = mappedByteBuffer.getLong(i + 16);
            long[] history = new long[]{key, depth, value};
            table.put(history);
        }
        time = System.currentTimeMillis() - time;
        System.out.println("File loaded in " + time + " ms");
        return table;
    }

    public int toFile(String fileName) throws IOException {
        long time = System.currentTimeMillis();
        RandomAccessFile f = new RandomAccessFile(fileName, "rw");
        f.setLength(0);
        final int bufferSize = 1000 * 3 * Long.BYTES;
        int fileSize = bufferSize;
        ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
        for (long[] history : transpositionTable) {
            if (history != null) {
                for (long v : history) {
                    buffer.putLong(v);
                }
                if (!buffer.hasRemaining()) {
                    f.write(buffer.array());
                    buffer.clear();
                    fileSize += bufferSize;
                }
            }
        }
        f.write(buffer.array());
        f.close();
        time = System.currentTimeMillis() - time;
        System.out.println("File saved in " + time + " ms, Size: " + fileSize + " bytes");
        return fileSize;
    }

    public static void main(String[] args) throws IOException {
        TranspositionTable table = fromFile(new File("test.dat"), 26);
        System.out.println(table);
    }

}
