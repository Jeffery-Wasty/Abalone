package ca.bcit.abalone.game;

import java.io.File;
import java.io.FileNotFoundException;
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

    public static void test(String filename) throws IOException {
        AbaloneGame game = createStateFromInput(new File(filename + ".input"));
        List<AbaloneGame> result = createStateFromBoard(new File(filename + ".board"));
        System.out.println(game);

        System.out.println("Size: " + game.actions(game.state).length + "-" + result.size());

        int count = 0;
        List<String> moves = new ArrayList<>();
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
                System.out.println("Cannot match action " + Arrays.deepToString(action.getNewPieces()));
            } else {
                moves.add(action.toString());
            }
        }
        System.out.println("Matched: " + count + "/" + result.size());
        createMoveFile(moves, filename);
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
        TreeSet<String> pieces = new TreeSet<>((a, b) -> {
            if (a.charAt(2) != b.charAt(2)) {
                return a.charAt(2) - b.charAt(2);
            }
            if (a.charAt(0) != b.charAt(0)) {
                return a.charAt(0) - b.charAt(0);
            }
            return a.charAt(1) - b.charAt(1);
        });
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

    public static void createMoveFile(List<String> moves, String name) throws IOException {
        Files.write(Paths.get(name + ".move"),
                moves,
                Charset.forName("UTF-8")
        );
    }

    public static void createTestInputFile(AbaloneGame game, String name) throws IOException {
        Files.write(Paths.get(name + ".input"),
                Arrays.asList(
                        getPlayer(game.getPlayer()),
                        createStringFromGame(game)
                ),
                Charset.forName("UTF-8")
        );
    }

    public static void createTestBoardFile(AbaloneGame game, String name) throws IOException {
        AbaloneGame.Action[] actions = game.actions(game.state);
        TreeSet<String> boards = new TreeSet<>();
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
            System.out.println(String.format("Generated random game state and saved to %s.input and %s.board", name, name));
        }
    }

    public static AbaloneGame randomAbaloneGame() {
        Random rnd = new Random();
        AbaloneGame game = new AbaloneGame(new AbaloneGame.State(AbaloneGame.BELGIAN_DAISY_INITIAL_STATE, 1), -1);
        int level = rnd.nextInt(150) + 100;
        for (int i = 0; i < level; i++) {
            int rndIndex = rnd.nextInt(game.actions().length);
            AbaloneGame.Action action = game.actions()[rndIndex];
            game = game.result(action);
        }
        return game;
    }

    public static String getPlayer(char p) {
        return p == AbaloneGame.BLACK ? "b" : "w";
    }

    public static void runTest(int from, int to) throws IOException {
        for (int i = from; i <= to; i++) {
            test("./test/Test" + i);
        }
    }

    public static void generateBoardAndMoveBaseOnInput(int from, int to) throws IOException {
        for (int i = from; i <= to; i++) {
            generateBoardAndMoveBaseOnInput("./test/Test" + i);
        }
    }

    public static void generateBoardAndMoveBaseOnInput(String filename) throws IOException {
        AbaloneGame game = createStateFromInput(new File(filename + ".input"));

        List<String> moves = new ArrayList<>();
        for (AbaloneGame.Action action : game.actions(game.state)) {
            moves.add(action.toString());
        }
        createTestBoardFile(game, filename);
        createMoveFile(moves, filename);
        System.out.println(String.format("Generated %s.board and %s.move files, %d legal moves generated", filename, filename, moves.size()));
    }

    public static void printMenu() {
        System.out.println("Choose one of the options: ");
        System.out.println("1. Generate .board and .move files by taking .input files in the /test/ folder");
        System.out.println("2. Compare and match the .input and .board files in the /test/ folder");
        System.out.println("3. Create random game state and the corresponding .input/.board files into the /test/ folder, please note that the existing files will be replaced if the name is duplicated");
        System.out.println("Enter q to exit");
    }

    public static int[] readParameters(Scanner scanner) {
        System.out.println("Input the range of test files to be tested, the starting index and the end index separate by a space.\n(If running test files 5 to 12, input \"5 12\")");
        String line = scanner.nextLine().trim();
        String[] tokens = line.split(" ");
        int from = Integer.parseInt(tokens[0]);
        int to = Integer.parseInt(tokens[1]);
        return new int[]{from, to};
    }

    public static int readOption(Scanner scanner) {
        String line = scanner.nextLine().trim();
        if (line.equals("q")) {
            return -1;
        }
        return Integer.parseInt(line);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            try {
                int option = readOption(scanner);
                if (option == -1) {
                    break;
                }
                if (option > 3 || option < 1) {
                    throw new IllegalArgumentException("Invalid Input");
                }
                int[] params = readParameters(scanner);
                switch (option) {
                    case 1:
                        generateBoardAndMoveBaseOnInput(params[0], params[1]);
                        break;
                    case 2:
                        runTest(params[0], params[1]);
                        break;
                    case 3:
                        createRandomTests(params[0], params[1]);
                        break;
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        scanner.close();
    }

}
