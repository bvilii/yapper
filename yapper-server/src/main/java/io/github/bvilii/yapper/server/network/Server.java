package io.github.bvilii.yapper.server.network;

import io.github.bvilii.yapper.server.ui.ClientHandlerListener;
import io.github.bvilii.yapper.server.ui.ServerListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final int port;
    private ServerListener serverListener;
    private ServerSocket serverSocket;
    private ClientHandlerListener clientHandlerListener;

    public Server(int port, ServerListener serverListener,  ClientHandlerListener clientHandlerListener) {
        this.port = port;
        this.serverListener = serverListener;
        this.clientHandlerListener = clientHandlerListener;
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            serverListener.onServerStart();

        } catch (IOException e) {
            serverListener.onServerStartError(e);
        }

        while (true) {
            try {
                Socket  clientSocket = serverSocket.accept();

                Thread.ofVirtual().start(new ClientHandler(clientSocket, clientHandlerListener));


            } catch (Exception e) {


            }
        }
    }
}