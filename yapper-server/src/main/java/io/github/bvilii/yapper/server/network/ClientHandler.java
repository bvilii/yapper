package io.github.bvilii.yapper.server.network;


import io.github.bvilii.yapper.server.service.MessageListener;
import io.github.bvilii.yapper.server.ui.ClientLifecycleListener;
import io.github.bvilii.yapper.shared.model.Message;

import java.io.IOException;

public class ClientHandler implements Runnable {
    private final ClientSession session;
    private final ClientLifecycleListener clientLifecycleListener;
    private final MessageListener messageListener;

    public ClientHandler(ClientSession session , ClientLifecycleListener clientLifecycleListener,
                         MessageListener messageListener) {

        this.session = session;
        this.clientLifecycleListener = clientLifecycleListener;
        this.messageListener = messageListener;
    }


    @Override
    public void run() {
        try {

            clientLifecycleListener.onClientConnected(session);

            Message message;
            while ((message = session.receiveMessage()) != null) {

                messageListener.onMessageReceived(session, message);

            }

            clientLifecycleListener.onClientDisconnected(session);

        } catch (IOException e) {

            clientLifecycleListener.onClientConnectionError(session, e);
        }
    }

}