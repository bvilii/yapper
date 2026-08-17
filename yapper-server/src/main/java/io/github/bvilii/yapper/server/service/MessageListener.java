package io.github.bvilii.yapper.server.service;

import io.github.bvilii.yapper.server.network.ClientSession;
import io.github.bvilii.yapper.shared.model.message.Message;

public interface MessageListener {
    void onMessageReceived(ClientSession session, Message message);

    void onClientDisconnected(ClientSession session);
}
