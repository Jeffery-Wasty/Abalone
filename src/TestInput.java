import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TestInput {

    static final Map<Character, Integer> ROW_MAP = new HashMap<Character, Integer>() {{
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

    private static void test(String inputFile, String boardFile) throws FileNotFoundException {
        AbaloneGame game = createStateFromInput(new File(inputFile));
        List<AbaloneGame> result = createStateFromBoard(new File(boardFile));

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
                System.out.println("Cannot match state " + game.state);
            }
        }
        System.out.println("Matched: " + count + "/" + result.size());
    }

    private static ArrayList<AbaloneGame> createStateFromBoard(File input) throws FileNotFoundException {
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

    private static AbaloneGame createStateFromString(String line, int turn) {
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

    public static void main(String[] args) throws FileNotFoundException {
        test("Test1.input", "Test1.board");
        test("Test2.input", "Test2.board");
    }

}
