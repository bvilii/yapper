package io.github.bvilii.yapper.server.app;

import io.github.bvilii.yapper.server.config.DatabaseConnection;
import io.github.bvilii.yapper.server.network.Server;
import io.github.bvilii.yapper.server.repository.UserRepository;
import io.github.bvilii.yapper.server.service.*;
import io.github.bvilii.yapper.server.ui.ClientLifecycleListener;
import io.github.bvilii.yapper.server.ui.ServerListener;
import io.github.bvilii.yapper.server.ui.ServerUI;

public class Main {
    public static void main(String[] args) {
        int port = 5000;

        DatabaseConnection databaseConnection = new DatabaseConnection();
        UserRepository userRepository = new UserRepository(databaseConnection.getJdbi());

        ClientRegistry registry = new ClientRegistry();

        BroadcastService broadcastService = new BroadcastService(registry);
        LoginService loginService = new LoginService(registry, userRepository);
        RegisterService registerService = new RegisterService(userRepository, registry);
        MessageListener messageListener = new MessageRouter(registry, loginService, broadcastService, registerService);

        ServerListener serverListener = new ServerUI();
        ClientLifecycleListener clientLifecycleListener = new ServerUI();

        Server server = new Server(port, serverListener, clientLifecycleListener, messageListener);
        server.start();
    }
}