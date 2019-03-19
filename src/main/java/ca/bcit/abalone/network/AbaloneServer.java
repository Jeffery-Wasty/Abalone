package ca.bcit.abalone.network;

import ca.bcit.abalone.game.AbaloneGame;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

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

        @Override
        String handleInput(String input) {
            String[] tokens = input.split(" ");
            System.out.println("Received: " + Arrays.toString(tokens));
            String endpoint = tokens[0];
            switch (endpoint) {
                case "game-state":
                    return abaloneGame.toString();
                default:
                    return endpoint + " Not-Found";
            }
        }

    }

    public static void main(String[] args) throws IOException {
        new AbaloneServer(1337).startListening();
    }

}
