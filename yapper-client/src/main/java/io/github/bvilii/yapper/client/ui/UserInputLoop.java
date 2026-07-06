package io.github.bvilii.yapper.client.ui;

import io.github.bvilii.yapper.client.service.ChatService;

import java.util.Scanner;

public class UserInputLoop {
    private final Scanner scanner;
    private final ChatService chatService;

    public UserInputLoop(Scanner scanner, ChatService chatService) {
        this.scanner = scanner;
        this.chatService = chatService;
    }

    public void start() {
        while (true) {

            String line = scanner.nextLine();
            chatService.onUserInput(line);
        }
    }

}
