package io.github.bvilii.yapper.client.app;


import io.github.bvilii.yapper.client.network.ServerConnection;
import io.github.bvilii.yapper.client.network.ServerHandler;
import io.github.bvilii.yapper.client.network.ServerSession;
import io.github.bvilii.yapper.client.service.MessageRouter;
import io.github.bvilii.yapper.client.service.LoginService;
import io.github.bvilii.yapper.client.service.RegisterService;
import io.github.bvilii.yapper.client.service.TokenLoginService;
import io.github.bvilii.yapper.client.storage.TokenStorage;
import io.github.bvilii.yapper.client.ui.ClientDisplay;
import io.github.bvilii.yapper.client.ui.UserInputLoop;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {

        ClientDisplay display = new ClientDisplay();

        ServerConnection connection;
        try {
            connection = ServerConnection.connect("localhost", 5000);

        } catch (IOException e) {
            System.out.println("Could not connect to server");
            return;
        }

        TokenStorage tokenStorage = new TokenStorage();

        ServerSession session = new ServerSession(connection);

        TokenLoginService tokenLoginService = new TokenLoginService(session, tokenStorage);
        LoginService loginService = new LoginService(session, tokenStorage);
        RegisterService registerService = new RegisterService(session, tokenStorage);

        MessageRouter service = new MessageRouter(session, display, loginService, registerService, tokenLoginService);

        ServerHandler handler = new ServerHandler(session, service);
        Thread serverThread = new Thread(handler);
        serverThread.start();

        Scanner scanner = new Scanner(System.in);


        System.out.println("=========================");
        System.out.println("    WELCOME TO YAPPER    ");
        System.out.println("=========================");
        System.out.println();

        boolean auth_success = false;

        String token = tokenStorage.getToken();
        if(token != null) {
            try {
                auth_success = tokenLoginService.attemptTokenLogin(token).get();

            } catch (InterruptedException | ExecutionException e) {
                System.out.println("Error while attempting to token login. Error message: " + e.getMessage());
                return;
            }
        }



        while (!auth_success) {

            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.print("Select an option (1-2): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": {

                    System.out.println("Please enter your username: ");
                    String username = scanner.nextLine().trim();

                    System.out.println("Please enter your password: ");
                    String password = scanner.nextLine();

                    try {
                        auth_success = loginService.attemptLogin(username, password).get();

                        if (auth_success) {
                            System.out.println("You have successfully logged in!");
                        } else {
                            System.out.println("Incorrect username or password!");
                        }

                    } catch (InterruptedException | ExecutionException e) {
                        System.out.println("Error while attempting to login. Error message: " + e.getMessage());
                        return;
                    }

                    break;
                }

                case "2": {
                    System.out.println("Please enter a username: ");
                    String username = scanner.nextLine().trim();

                    System.out.println("Please enter a password: ");
                    String password = scanner.nextLine();

                    try {
                        auth_success = registerService.attemptRegister(username, password).get();

                        if (auth_success) {
                            System.out.println("You have successfully registered!");
                        } else {
                            System.out.println("Username is already taken!");
                        }

                    } catch (InterruptedException | ExecutionException e) {
                        System.out.println("Error while attempting to register. Error message: " + e.getMessage());
                        return;
                    }
                    break;
                }

                default: {
                    System.out.println("Invalid Option! Please try again!");
                    break;
                }
            }
        }


        UserInputLoop inputLoop = new UserInputLoop(scanner, service);
        inputLoop.start();


    }
}
