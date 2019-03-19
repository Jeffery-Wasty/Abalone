package ca.bcit.abalone.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public abstract class ServerHandler<T extends ClientSocket> {

    protected final ServerSocket serverSocket;

    ServerHandler(int port) throws IOException {
        this.serverSocket = new java.net.ServerSocket(port);
    }

    public void startListening() {
        System.out.println("Server listening on port " + serverSocket.getLocalPort());
        while (!serverSocket.isClosed()) {
            try {
                T client = createClientSocket(serverSocket.accept());
                System.out.println("New connection from " + client.clientSocket.getLocalAddress());
                new Thread(client::startListening).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    abstract T createClientSocket(Socket socket) throws IOException;

}
