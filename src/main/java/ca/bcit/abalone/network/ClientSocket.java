package ca.bcit.abalone.network;

import java.io.*;
import java.net.Socket;

public abstract class ClientSocket {

    protected final Socket clientSocket;
    protected final BufferedReader in;
    protected final BufferedWriter out;

    protected ClientSocket(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
    }

    protected void startListening() {
        try {
            String line;
            while ((line = in.readLine()) != null) {
                String response = handleInput(line);
                writeLine(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.close();
            System.out.println("Client disconnected");
        }
    }

    protected void writeLine(String line) {
        try {
            out.write(line + '\n');
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void close() {
        try {
            this.clientSocket.close();
            this.in.close();
            this.out.close();
        } catch (IOException ignored) {
        }
    }

    abstract String handleInput(String input);

}