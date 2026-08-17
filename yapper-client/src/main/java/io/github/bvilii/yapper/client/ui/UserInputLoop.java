package io.github.bvilii.yapper.client.ui;

import io.github.bvilii.yapper.client.service.MessageRouter;

import java.util.Scanner;

public class UserInputLoop {
    private final Scanner scanner;
    private final MessageRouter messageRouter;

    public UserInputLoop(Scanner scanner, MessageRouter messageRouter) {
        this.scanner = scanner;
        this.messageRouter = messageRouter;
    }

    public void start() {
        while (true) {

            String line = scanner.nextLine();
            messageRouter.onUserInput(line);
        }
    }

}
