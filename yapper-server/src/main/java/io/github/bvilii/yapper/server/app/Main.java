package io.github.bvilii.yapper.server.app;

import io.github.bvilii.yapper.server.config.DatabaseConnection;
import io.github.bvilii.yapper.server.network.Server;
import io.github.bvilii.yapper.server.repository.SessionRepository;
import io.github.bvilii.yapper.server.repository.UserRepository;
import io.github.bvilii.yapper.server.service.*;
import io.github.bvilii.yapper.server.ui.ClientLifecycleListener;
import io.github.bvilii.yapper.server.ui.ServerListener;
import io.github.bvilii.yapper.server.ui.ServerUI;
import org.jdbi.v3.core.Jdbi;

public class Main {
    public static void main(String[] args) {
        int port = 5000;

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Jdbi jdbi = databaseConnection.getJdbi();

        UserRepository userRepository = new UserRepository(jdbi);
        SessionRepository sessionRepository = new SessionRepository(jdbi);

        ClientRegistry registry = new ClientRegistry();

        BroadcastService broadcastService = new BroadcastService(registry);
        LoginService loginService = new LoginService(registry, userRepository, sessionRepository);
        RegisterService registerService = new RegisterService(userRepository, registry, sessionRepository);
        TokenLoginService tokenLoginService = new TokenLoginService(registry, sessionRepository);

        MessageListener messageListener = new MessageRouter(registry, loginService, broadcastService, registerService, tokenLoginService);

        ServerListener serverListener = new ServerUI();
        ClientLifecycleListener clientLifecycleListener = new ServerUI();

        Server server = new Server(port, serverListener, clientLifecycleListener, messageListener);
        server.start();
    }
}