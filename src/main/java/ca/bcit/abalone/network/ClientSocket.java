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
                final String finalLine = line;
                new Thread(() -> {
                    String response;
                    try {
                        response = processInput(finalLine);
                    } catch (Exception e) {
                        String[] tokens = Utility.splitByFirstIndexOf(finalLine, "?");
                        String endpoint = tokens[0];
                        String queryString = tokens[1];
                        Map<String, String> query = new HashMap<>();
                        if (queryString != null) {
                            for (String paramString : queryString.split("&")) {
                                String[] paramTokens = Utility.splitByFirstIndexOf(paramString, "=");
                                query.put(paramTokens[0], paramTokens[1]);
                            }
                        }
                        String request_id = query.get("request_id");
                        Map<String, String> resData = new HashMap<String, String>() {{
                            put("error", e.toString());
                        }};
                        if (request_id != null) {
                            resData.put("request_id", request_id);
                        }
                        response = buildResponse(endpoint, resData);
                        e.printStackTrace();
                    }
                    writeLine(response);
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.close();
            System.out.println("Client disconnected");
        }
    }

    protected void writeLine(String line) {
        System.out.println(line);
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

    private String processInput(String line) {
        /* build query params Map */
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
        // get response
        Map<String, String> response = handleInput(endpoint, query);
        // if the request is called with a request id
        String request_id = query.get("request_id");
        if (request_id != null) {
            response.put("request_id", request_id);
        }
        // build response object
        return buildResponse(endpoint, response);
    }

    public static String buildResponse(String endpoint, Map<String, String> response) {
        StringBuilder responseBuilder = new StringBuilder(endpoint + "?");
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