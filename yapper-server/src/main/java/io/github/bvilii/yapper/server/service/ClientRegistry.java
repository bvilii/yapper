package io.github.bvilii.yapper.server.service;

import io.github.bvilii.yapper.server.network.ClientSession;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ClientRegistry {
    private ConcurrentHashMap<String, ClientSession> sessions;

    public ClientRegistry() {
        sessions = new ConcurrentHashMap<>();
    }

    public void addSession(String username, ClientSession session) {
        sessions.put(username, session);
    }

    public void removeSession(String username) {
        sessions.remove(username);
    }

    public boolean isUsernameTaken(String username) {
        return sessions.containsKey(username);
    }

    public Collection<ClientSession> getSessions() {
        return List.copyOf(sessions.values());
    }
}
