package io.github.bvilii.yapper.server.ui;

import io.github.bvilii.yapper.server.network.ClientSession;

import java.io.IOException;

public interface ClientLifecycleListener {
    void onClientConnected(ClientSession session);

    void onClientDisconnected(ClientSession session);

    void onClientSetupFailed(Exception e);

    void onClientConnectionError(ClientSession session, IOException e);
}
