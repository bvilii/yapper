package io.github.bvilii.yapper.server.app;

import io.github.bvilii.yapper.server.network.Server;
import io.github.bvilii.yapper.server.service.BroadcastService;
import io.github.bvilii.yapper.server.service.MessageRouter;
import io.github.bvilii.yapper.server.service.ClientRegistry;
import io.github.bvilii.yapper.server.service.LoginService;
import io.github.bvilii.yapper.server.ui.ClientLifecycleListener;
import io.github.bvilii.yapper.server.ui.ServerListener;
import io.github.bvilii.yapper.server.ui.ServerUI;
import io.github.bvilii.yapper.server.service.MessageListener;

public class Main {
    public static void main(String[] args) {
        int port = 5000;

        ClientRegistry registry = new ClientRegistry();
        BroadcastService broadcastService = new BroadcastService(registry);
        LoginService loginService = new LoginService(registry);
        MessageListener messageListener = new MessageRouter(registry, loginService, broadcastService);

        ServerListener serverListener = new ServerUI();
        ClientLifecycleListener clientLifecycleListener = new ServerUI();

        Server server = new Server(port, serverListener, clientLifecycleListener, messageListener);
        server.start();
    }
}