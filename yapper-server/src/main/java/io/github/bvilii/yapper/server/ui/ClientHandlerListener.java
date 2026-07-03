package io.github.bvilii.yapper.server.ui;

public interface ClientHandlerListener {
    void onClientConnected();
    void onClientConnectionError(Exception e);
}
