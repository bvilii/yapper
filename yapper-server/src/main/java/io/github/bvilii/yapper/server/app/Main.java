package io.github.bvilii.yapper.server.app;

import io.github.bvilii.yapper.server.network.Server;
import io.github.bvilii.yapper.server.service.BroadcastService;
import io.github.bvilii.yapper.server.service.ChatService;
import io.github.bvilii.yapper.server.service.ClientRegistry;
import io.github.bvilii.yapper.server.service.LoginService;
import io.github.bvilii.yapper.server.ui.ClientLifecycleListener;
import io.github.bvilii.yapper.server.ui.ServerListener;
import io.github.bvilii.yapper.server.ui.ServerUI;
import io.github.bvilii.yapper.server.service.MessageListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        int port;

        Properties config  = new Properties();
        try(InputStream in = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
            config.load(in);

        } catch (IOException e) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter port:");
            port = Integer.parseInt(scanner.nextLine());
        }

        port = Integer.parseInt(config.getProperty("server.port"));

        ClientRegistry registry = new ClientRegistry();
        BroadcastService broadcastService = new BroadcastService(registry);
        LoginService loginService = new LoginService(registry);
        MessageListener messageListener = new ChatService(registry, loginService, broadcastService);

        ServerListener serverListener = new ServerUI();
        ClientLifecycleListener clientLifecycleListener = new ServerUI();

        Server server = new Server(port, serverListener, clientLifecycleListener, messageListener);
        server.start();
    }
}