package ca.bcit.abalone.network;

import ca.bcit.abalone.game.Utility;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

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
                String response = preProcessInput(line);
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

    private String preProcessInput(String line) {
        String[] tokens = Utility.splitByFirstIndexOf(line, "?");
        String endpoint = tokens[0];
        String queryString = tokens[1];
        Map<String, String> query = new HashMap<>();
        if (queryString != null) {
            for (String paramString : queryString.split("&")) {
                String[] paramTokens = Utility.splitByFirstIndexOf(paramString, "=");
                query.put(paramTokens[0], paramTokens[1]);
            }
        }
        StringBuilder responseBuilder = new StringBuilder(endpoint + "?");
        Map<String, String> response = handleInput(endpoint, query);
        for (Map.Entry<String, String> entry : response.entrySet()) {
            responseBuilder.append(entry.getKey());
            responseBuilder.append("=");
            responseBuilder.append(entry.getValue());
            responseBuilder.append('&');
        }
        responseBuilder.deleteCharAt(responseBuilder.length() - 1);
        return responseBuilder.toString();
    }

    abstract Map<String, String> handleInput(String endpoint, Map<String, String> query);

}