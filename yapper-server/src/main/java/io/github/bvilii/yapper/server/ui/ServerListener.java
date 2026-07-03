package io.github.bvilii.yapper.server.ui;

public interface ServerListener {
    void onServerStart();
    void onServerStartError(Exception e);
}
