package ca.bcit.abalone.network;

import ca.bcit.abalone.ai.AbaloneHeuristicJeff;
import ca.bcit.abalone.ai.TimeLimitSearchAI;
import ca.bcit.abalone.game.AbaloneGame;
import ca.bcit.abalone.game.Utility;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AbaloneAIServer extends ServerHandler<AbaloneAIServer.AbaloneClient> {

    private TimeLimitSearchAI<Character, AbaloneGame.State, AbaloneGame.Action, AbaloneGame> ai = new TimeLimitSearchAI<>(AbaloneHeuristicJeff.simplePositionWeightedHeuristic);

    private String getNextStateByAI(char[] state, int turnLimit, int timeLimit, int turn) {
        AbaloneGame game = new AbaloneGame(new AbaloneGame.State(state, turn), turnLimit);
        AbaloneGame.Action action = ai.search(game, timeLimit * 1000 - 100, 3, 1);

        byte[][] result = action.getNewPieces();
        for (byte[] move : result) {
            switch ((char) move[1]) {
                case AbaloneGame.BLACK:
                    move[1] = 2;
                    break;
                case AbaloneGame.WHITE:
                    move[1] = 1;
                    break;
                case AbaloneGame.EMPTY:
                    move[1] = 0;
            }
        }
        return Arrays.deepToString(result);
    }

    private char[] getCharStateFromIntState(Integer[] input) {
        char[] state = new char[61];
        for (int i = 0; i < state.length; i++) {
            switch (input[i]) {
                case 0:
                    state[i] = AbaloneGame.EMPTY;
                    break;
                case 1:
                    state[i] = AbaloneGame.WHITE;
                    break;
                case 2:
                    state[i] = AbaloneGame.BLACK;
                    break;
            }
        }
        return state;
    }

    public AbaloneAIServer(int port) throws IOException {
        super(port);
    }

    @Override
    AbaloneClient createClientSocket(Socket socket) throws IOException {
        return new AbaloneClient(socket);
    }

    class AbaloneClient extends ClientSocket {

        private AbaloneClient(Socket clientSocket) throws IOException {
            super(clientSocket);
        }

        Map<String, String> handleInput(String endpoint, Map<String, String> query) {
            System.out.println(endpoint + ": " + query);
            switch (endpoint) {
                case "next-move":
                    char[] state = getCharStateFromIntState(Utility.toIntArray(query.get("state")));
                    int timeLimit = Integer.parseInt(query.get("timeLimit"));
                    int turnLimit = Integer.parseInt(query.get("turnLimit"));
                    int turn = Integer.parseInt(query.get("turn"));

                    return new HashMap<String, String>() {{
                        try {
                            String result = getNextStateByAI(state, turnLimit * 2, timeLimit, turn);
                            put("action", result);
                        } catch (NullPointerException e) {
                            put("error", "No Action generated, either spent too much time on first search or the game has reached terminal");
                        }
                    }};
                default:
                    return new HashMap<String, String>() {{
                        put("error", "Not Found");
                    }};
            }
        }

    }

    public static void main(String[] args) throws IOException {
        new AbaloneAIServer(1337).startListening();
    }

}
