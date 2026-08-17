package io.github.bvilii.yapper.server.service;

import io.github.bvilii.yapper.server.network.ClientSession;
import io.github.bvilii.yapper.server.repository.UserRepository;
import io.github.bvilii.yapper.shared.model.message.LoginRequestMessage;
import io.github.bvilii.yapper.shared.model.message.LoginResponseMessage;
import io.github.bvilii.yapper.shared.model.message.MessageType;

public class LoginService {
    private final ClientRegistry registry;
    private final UserRepository userRepository;

    public LoginService(ClientRegistry registry, UserRepository userRepository) {
        this.registry = registry;
        this.userRepository = userRepository;
    }

    public void handleLogin(ClientSession session, LoginRequestMessage message) {
        String username = message.getUsername();
        String password = message.getPassword();

        if(!userRepository.verifyPassword(username, password)){
            session.sendMessage(new LoginResponseMessage(MessageType.LOGIN_ERROR, "Invalid username or password!"));
            return;
        }

        if(registry.isUserOnline(username)){
            session.sendMessage(new LoginResponseMessage(MessageType.LOGIN_ERROR, "User is already online!"));
            return;
        }

        session.setUsername(username);
        registry.addSession(username, session);
        session.sendMessage(new LoginResponseMessage(MessageType.LOGIN_SUCCESS, "Login successful!"));
    }
}
