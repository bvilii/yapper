package io.github.bvilii.yapper.server.service;

import io.github.bvilii.yapper.server.network.ClientSession;
import io.github.bvilii.yapper.server.repository.UserRepository;
import io.github.bvilii.yapper.shared.model.message.MessageType;
import io.github.bvilii.yapper.shared.model.message.RegisterRequestMessage;
import io.github.bvilii.yapper.shared.model.message.RegisterResponseMessage;

public class RegisterService {
    private final UserRepository userRepository;
    private final ClientRegistry registry;

    public RegisterService(UserRepository userRepository, ClientRegistry registry) {
        this.userRepository = userRepository;
        this.registry = registry;
    }

    public void handleRegister(RegisterRequestMessage message, ClientSession session) {

        String username = message.getUsername();

        if (userRepository.existsByUsername(username)) {
            session.sendMessage(new RegisterResponseMessage(MessageType.REGISTER_ERROR, "Username is already taken!"));

        } else {
            userRepository.saveUser(username, message.getPassword());
            session.setUsername(username);
            session.sendMessage(new RegisterResponseMessage(MessageType.REGISTER_SUCCESS, "User registered successfully!"));
            registry.addSession(username, session);
        }
    }
}
