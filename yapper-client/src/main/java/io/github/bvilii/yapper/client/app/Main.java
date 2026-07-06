package io.github.bvilii.yapper.client.app;


import io.github.bvilii.yapper.client.network.ServerConnection;
import io.github.bvilii.yapper.client.network.ServerHandler;
import io.github.bvilii.yapper.client.network.ServerSession;
import io.github.bvilii.yapper.client.service.ChatService;
import io.github.bvilii.yapper.client.service.LoginService;
import io.github.bvilii.yapper.client.ui.ClientDisplay;
import io.github.bvilii.yapper.client.ui.UserInputLoop;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {
        ClientDisplay display = new ClientDisplay();

        ServerConnection connection;
        try {
            connection = ServerConnection.connect("7.tcp.eu.ngrok.io", 20039);

        } catch (IOException e) {
            System.out.println("Could not connect to server");
            return;
        }

        ServerSession session = new ServerSession(connection);

        LoginService loginService = new LoginService(session);
        ChatService service = new ChatService(session, display, loginService);

        ServerHandler handler = new ServerHandler(session, service);
        Thread serverThread = new Thread(handler);
        serverThread.start();

        Scanner scanner = new Scanner(System.in);
        System.out.println("WELCOME TO YAPPER");

        boolean success = false;
        do {
            System.out.println("PLEASE ENTER A USERNAME:");
            String username = scanner.nextLine();
            try {
                success = loginService.attemptLogin(username).get();
                if (!success) {
                    System.out.println("USERNAME ALREADY TAKEN! TRY AGAIN!");
                }else{
                    System.out.println("LOGIN SUCCESSFUL!");
                }

            } catch (InterruptedException e) {
                System.out.println("Error while attempting to login. Error message: " + e.getMessage());
                return;
            } catch (ExecutionException e) {
                System.out.println("Error while attempting to login. Error message: " + e.getMessage());
                return;
            }

        } while (!success);


        UserInputLoop inputLoop = new UserInputLoop(scanner, service);
        inputLoop.start();
    }
}
