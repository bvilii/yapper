package io.github.bvilii.yapper.server.service;

import io.github.bvilii.yapper.server.model.Message;
import io.github.bvilii.yapper.server.network.ClientSession;

public interface MessageListener {
    void onMessageReceived(ClientSession session, Message message);
}
