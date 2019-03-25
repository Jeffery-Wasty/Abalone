package ca.bcit.abalone.network;

import ca.bcit.abalone.ai.AlphaBetaAI;
import ca.bcit.abalone.game.AbaloneAction;
import ca.bcit.abalone.game.AbaloneGame;
import ca.bcit.abalone.game.Utility;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AbaloneServer extends ServerHandler<AbaloneServer.AbaloneClient> {

    private AbaloneGame abaloneGame = new AbaloneGame(new AbaloneGame.State(AbaloneGame.STANDARD_INITIAL_STATE, 1), -1);
    private AlphaBetaAI<Character, AbaloneGame.State, AbaloneGame.Action> ai = new AlphaBetaAI<>();
    private char[] initialState = AbaloneGame.STANDARD_INITIAL_STATE;
    private int timeLimit = -1;
    private int turnLimit = -1;
    private String gameMode = "pve";
    private Character playerColor = '@';

    public AbaloneServer(int port) throws IOException {
        super(port);
    }

    private String playerNext(AbaloneAction action) {

        if (playerColor.equals(abaloneGame.getPlayer())) {
            String result = next(action);
            if (this.gameMode.equals("pve")) {
                new Thread(() -> {
                    // TODO: add time limit to ai here
                    AbaloneGame.Action abaloneAction = ai.play(abaloneGame);
                    next(abaloneAction);
                }).start();
            }
            return result;
        } else {
            return "false";
        }
    }

    private String next(AbaloneAction action) {
        AbaloneGame.Action transitionAction = abaloneGame.isValidAction(action);
        if (transitionAction != null) {
            next(transitionAction);
            return "true";
        } else {
            return "false";
        }
    }

    private void next(AbaloneGame.Action action) {
        abaloneGame = abaloneGame.result(action);
        forEachClient((client) -> {
            client.writeLine(ClientSocket.buildResponse("game-state", new HashMap<String, String>() {{
                put("state", getBoardState());
                put("turn", getTurn());
            }}));
        });
    }

    private String getBoardState() {
        int[] state = new int[61];
        char[] stateChar = abaloneGame.state.getBoard();
        for (int i = 0; i < state.length; i++) {
            switch (stateChar[i]) {
                case AbaloneGame.BLACK:
                    state[i] = 2;
                    break;
                case AbaloneGame.WHITE:
                    state[i] = 1;
                    break;
                case AbaloneGame.EMPTY:
                    state[i] = 0;
                    break;
            }
        }
        return Arrays.toString(state);
    }

    private String getTurn() {
        return String.valueOf(abaloneGame.state.getTurn());
    }

    private String newGame(String boardLayout, String gameMode, String playerColor, String turnLimitString, String timeLimitString) {
        setInitialState(boardLayout);
        int turnLimit = Integer.valueOf(turnLimitString);
        int timeLimit = Integer.valueOf(timeLimitString);
        if (!gameMode.equals("pvp") && !gameMode.equals("pve")) {
            throw new IllegalArgumentException("Invalid game mode");
        }
        if (!playerColor.equals("black") && !playerColor.equals("white")) {
            throw new IllegalArgumentException("Invalid player color");
        }
        // passed validation, create new game
        this.turnLimit = turnLimit;
        this.timeLimit = timeLimit;
        this.gameMode = gameMode;
        this.setPlayerColor(playerColor);
        // create a new game
        abaloneGame = new AbaloneGame(new AbaloneGame.State(this.initialState, 1), this.turnLimit);

        return getBoardState();
    }

    private void setInitialState(String boardLayout) {
        switch (boardLayout) {
            case "standard":
                initialState = AbaloneGame.STANDARD_INITIAL_STATE;
                break;
            case "german_daisy":
                initialState = AbaloneGame.GERMAN_DAISY_INITIAL_STATE;
                break;
            case "belgian_daisy":
                initialState = AbaloneGame.BELGIAN_DAISY_INITIAL_STATE;
                break;
            default:
                throw new IllegalArgumentException("Invalid Board Layout");
        }
    }

    private void setPlayerColor(String playerColor) {
        if (playerColor.equals("black")) {
            this.playerColor = '@';
        } else if (playerColor.equals("white")) {
            this.playerColor = 'O';
        }
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
                    String succeed = "false";
                    Integer[] moves = Utility.toIntArray(query.get("moves"));
                    int[] result = abaloneGame.isValidUIMove(Arrays.asList(moves));

                    if (result[0] > 0) {
                        succeed = playerNext(new AbaloneAction(result[0], result[1], result[2]));
                    }
                    String finalStatus = succeed;
                    return new HashMap<String, String>() {{
                        put("succeed", finalStatus);
                    }};
                case "game-state":
                    return new HashMap<String, String>() {{
                        put("state", getBoardState());
                        put("turn", getTurn());
                    }};
                case "new-game":
                    return new HashMap<String, String>() {{
                        put("state", newGame(query.get("boardLayout"), query.get("gameMode"), query.get("playerColor"), query.get("turnLimit"), query.get("timeLimit")));
                        put("turn", getTurn());
                    }};
                default:
                    return new HashMap<String, String>() {{
                        put("error", "Not Found");
                    }};
            }
        }

    }

    public static void main(String[] args) throws IOException {
        new AbaloneServer(1337).startListening();
    }

}
