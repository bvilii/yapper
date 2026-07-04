package io.github.bvilii.yapper.server.ui;

import java.io.IOException;

public interface ServerListener {
    void onServerStart();
    void onServerStartError(IOException e);
}
