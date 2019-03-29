package ca.bcit.abalone.ai;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class HistoryTable {

    private final HashMap<String, Integer> table = new HashMap<>();

    public static HistoryTable fromFile(File file) throws FileNotFoundException {
        HistoryTable historyTable = new HistoryTable();
        Scanner scanner = new Scanner(file);
        String line;
        while (scanner.hasNext()) {
            line = scanner.nextLine();
            String[] tokens = line.split("=");
            historyTable.table.put(tokens[0], Integer.parseInt(tokens[1]));
        }
        return historyTable;
    }

    public void saveToFile(String filename) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        table.forEach((key, value) -> {
            lines.add(key + "=" + value);
        });
        Files.write(Paths.get(filename),
                lines,
                Charset.forName("UTF-8")
        );
    }

    public HashMap<String, Integer> getTable() {
        return table;
    }
}
