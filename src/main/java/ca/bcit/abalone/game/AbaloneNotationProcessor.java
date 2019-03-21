package ca.bcit.abalone.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class AbaloneNotationProcessor {

    public static final Map<Character, Integer> ROW_MAP = new HashMap<Character, Integer>() {{
        put('I', -5);
        put('H', 1);
        put('G', 8);
        put('F', 16);
        put('E', 25);
        put('D', 34);
        put('C', 42);
        put('B', 49);
        put('A', 55);
    }};

    public static final String[] TO_STANDARD_MAP = new String[]{
            "I5", "I6", "I7", "I8", "I9",
            "H4", "H5", "H6", "H7", "H8", "H9",
            "G3", "G4", "G5", "G6", "G7", "G8", "G9",
            "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9",
            "E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9",
            "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8",
            "C1", "C2", "C3", "C4", "C5", "C6", "C7",
            "B1", "B2", "B3", "B4", "B5", "B6",
            "A1", "A2", "A3", "A4", "A5"
    };

    public static void test(String inputFile, String boardFile) throws FileNotFoundException {
        AbaloneGame game = createStateFromInput(new File(inputFile));
        List<AbaloneGame> result = createStateFromBoard(new File(boardFile));
        System.out.println(game);

        game.isTerminal = false;
        System.out.println("Size: " + game.actions(game.state).length + "-" + result.size());

        int count = 0;
        for (AbaloneGame.Action action : game.actions(game.state)) {
            char[] generatedState = game.result(action).state.getBoard();
            boolean found = false;
            for (AbaloneGame expected : result) {
                if (Arrays.equals(expected.state.getBoard(), generatedState)) {
                    count++;
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Cannot match state " + Arrays.toString(game.state.getBoard()));
            }
        }
        System.out.println("Matched: " + count + "/" + result.size());
    }

    public static ArrayList<AbaloneGame> createStateFromBoard(File input) throws FileNotFoundException {
        Scanner scanner;
        scanner = new Scanner(input);
        String line;
        ArrayList<AbaloneGame> results = new ArrayList<>();
        while (scanner.hasNext()) {
            line = scanner.nextLine();
            results.add(createStateFromString(line, 1));
        }
        return results;
    }

    private static AbaloneGame createStateFromInput(File input) throws FileNotFoundException {
        Scanner scanner;
        scanner = new Scanner(input);
        String player = scanner.nextLine();
        String pieces = scanner.nextLine();
        int turn;
        if (player.equals("b")) {
            turn = 1;
        } else {
            turn = 2;
        }
        return createStateFromString(pieces, turn);
    }

    public static AbaloneGame createStateFromString(String line, int turn) {
        String[] pieces = line.split(",");
        char[] state = new char[61];
        Arrays.fill(state, AbaloneGame.EMPTY);
        for (String piece : pieces) {
            char row = piece.charAt(0);
            int index = ROW_MAP.get(row) + Integer.parseInt(String.valueOf(piece.charAt(1)));
            char color;
            if (piece.charAt(2) == 'b') {
                color = AbaloneGame.BLACK;
            } else {
                color = AbaloneGame.WHITE;
            }
            state[index] = color;
        }

        return new AbaloneGame(new AbaloneGame.State(state, turn), -1);
    }

    public static String createStringFromGame(AbaloneGame game) {
        ArrayList<String> pieces = new ArrayList<>();
        char[] state = game.state.getBoard();
        for (int i = 0; i < state.length; i++) {
            char marble = state[i];
            if (marble != AbaloneGame.EMPTY) {
                pieces.add(TO_STANDARD_MAP[i] + getPlayer(marble));
            }
        }
        return String.join(",", pieces);
    }

    public static void createTestFiles(AbaloneGame game, String name) throws IOException {
        createTestInputFile(game, name);
        createTestBoardFile(game, name);
    }

    public static void createTestInputFile(AbaloneGame game, String name) throws IOException {
        Files.write(Paths.get(name + ".input"),
                Arrays.asList(
                        getPlayer(game.player),
                        createStringFromGame(game)
                ),
                Charset.forName("UTF-8")
        );
    }

    public static void createTestBoardFile(AbaloneGame game, String name) throws IOException {
        game.isTerminal = false;
        AbaloneGame.Action[] actions = game.actions(game.state);
        ArrayList<String> boards = new ArrayList<>(actions.length);
        for (AbaloneGame.Action action : actions) {
            AbaloneGame board = game.result(action);
            boards.add(createStringFromGame(board));
        }
        Files.write(Paths.get(name + ".board"),
                boards,
                Charset.forName("UTF-8")
        );
    }

    public static void createRandomTests(int from, int size) throws IOException {
        for (int i = 0; i < size; i++) {
            String name = "./test/Test" + (from + i);
            createTestFiles(randomAbaloneGame(), name);
        }
    }

    public static AbaloneGame randomAbaloneGame() {
        Random rnd = new Random();
        AbaloneGame game = new AbaloneGame(new AbaloneGame.State(AbaloneGame.BELGIAN_DAISY_INITIAL_STATE, 1), -1);
        int level = rnd.nextInt(150) + 100;
        for (int i = 0; i < level; i++) {
            int rndIndex = rnd.nextInt(game.validActions.length);
            AbaloneGame.Action action = game.validActions[rndIndex];
            game = game.result(action);
        }
        return game;
    }

    public static String getPlayer(char p) {
        return p == AbaloneGame.BLACK ? "b" : "w";
    }

    public static void runTest(int from, int to) throws FileNotFoundException {
        for (int i = from; i <= to; i++) {
            test("./test/Test" + i + ".input", "./test/Test" + i + ".board");
        }
    }

    public static void main(String[] args) throws IOException {
        runTest(1, 12);
//        createRandomTests(3, 10);
    }

}
