package ca.bcit.abalone.network;

import ca.bcit.abalone.game.AbaloneGame;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class AbaloneServer extends ServerHandler<AbaloneServer.AbaloneClient> {

    private AbaloneGame abaloneGame = new AbaloneGame(new AbaloneGame.State(AbaloneGame.STANDARD_INITIAL_STATE, 1), -1);

    public AbaloneServer(int port) throws IOException {
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
                case "game-state":
                    return new HashMap<String, String>() {{
                        put("data", abaloneGame.toString());
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
