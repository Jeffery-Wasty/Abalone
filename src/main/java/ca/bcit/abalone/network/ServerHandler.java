package ca.bcit.abalone.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

public abstract class ServerHandler<T extends ClientSocket> {

    public final ServerSocket serverSocket;
    public final ArrayList<T> clientList = new ArrayList<>();

    ServerHandler(int port) throws IOException {
        this.serverSocket = new java.net.ServerSocket(port);
    }

    public void startListening() {
        System.out.println("Server listening on port " + serverSocket.getLocalPort());
        while (!serverSocket.isClosed()) {
            try {
                T client = createClientSocket(serverSocket.accept());
                clientList.add(client);
                System.out.println("New connection from " + client.clientSocket.getLocalAddress());
                // start communicating with client
                new Thread(client::startListening).start();
            } catch (IOException e) {
                e.printStackTrace();
                this.close();
            }
        }
        this.close();
    }

    protected void forEachClient(Consumer<? super T> action) {
        clientList.forEach((client) -> {
            if (!client.clientSocket.isClosed()) {
                action.accept(client);
            }
        });
    }

    public void close() {
        System.out.println("Server closed");
    }

    abstract T createClientSocket(Socket socket) throws IOException;

}
