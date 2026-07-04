package io.github.bvilii.yapper.server.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientConnection {
    private final Socket clientSocket;
    private final BufferedReader in;
    private final PrintWriter out;

    private ClientConnection(Socket clientSocket, BufferedReader in, PrintWriter out) {
        this.clientSocket = clientSocket;
        this.in = in;
        this.out = out;
    }

    public static ClientConnection open(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        return new ClientConnection(clientSocket, in, out);
    }

    public String readLine() throws IOException {
        return in.readLine();
    }

    public void sendLine(String line) {
        out.println(line);
    }

}
