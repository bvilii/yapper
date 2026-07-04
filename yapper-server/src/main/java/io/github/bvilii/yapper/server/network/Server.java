package io.github.bvilii.yapper.server.network;

import io.github.bvilii.yapper.server.service.MessageListener;
import io.github.bvilii.yapper.server.ui.ClientLifecycleListener;
import io.github.bvilii.yapper.server.ui.ServerListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final int port;
    private final ServerListener serverListener;
    private final ClientLifecycleListener clientLifecycleListener;
    private final MessageListener messageListener;
    private ServerSocket serverSocket;

    public Server(int port, ServerListener serverListener,
                  ClientLifecycleListener clientLifecycleListener, MessageListener messageListener) {

        this.port = port;
        this.serverListener = serverListener;
        this.clientLifecycleListener = clientLifecycleListener;
        this.messageListener = messageListener;
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            serverListener.onServerStart();

        } catch (IOException e) {
            serverListener.onServerStartError(e);
            return;
        }

        while (!serverSocket.isClosed()) {
            try {
                Socket  clientSocket = serverSocket.accept();

                ClientConnection connection = ClientConnection.open(clientSocket);
                ClientSession session = new ClientSession(connection);
                ClientHandler handler = new ClientHandler(session, clientLifecycleListener, messageListener);

                Thread.ofVirtual().start(handler);

            } catch (IOException e) {
                if(serverSocket.isClosed()) {
                    break;
                }

                clientLifecycleListener.onClientSetupFailed(e);

            }
        }
    }
}