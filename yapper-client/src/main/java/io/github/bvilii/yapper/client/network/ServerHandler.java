package io.github.bvilii.yapper.client.network;


import io.github.bvilii.yapper.client.service.MessageRouter;
import io.github.bvilii.yapper.shared.model.message.Message;

import java.io.IOException;

public class ServerHandler implements Runnable {
    private final ServerSession session;
    private final MessageRouter service;

    public ServerHandler(ServerSession session, MessageRouter service) {
        this.session = session;
        this.service = service;
    }

    @Override
    public void run() {
        try{
            Message message;
            while((message = session.receiveMessage()) != null){
                service.onMessageFromServer(message);
            }

        } catch (IOException e) {
            service.onConnectionLost();
            System.exit(1);
        }
    }


}
