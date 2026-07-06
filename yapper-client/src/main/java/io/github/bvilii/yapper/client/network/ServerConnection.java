package io.github.bvilii.yapper.client.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnection {
    private final Socket serverSocket;
    private final BufferedReader in;
    private final PrintWriter out;

    private ServerConnection(Socket serverSocket, BufferedReader in, PrintWriter out) {
        this.serverSocket = serverSocket;
        this.in = in;
        this.out = out;
    }

    public static ServerConnection connect(String host, int port) throws IOException {
        Socket serverSocket = new Socket(host, port);
        BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
        return new ServerConnection(serverSocket, in, out);
    }

    public String readLine() throws IOException {
        return in.readLine();
    }

    public void sendLine(String line) {
        out.println(line);
    }
}
