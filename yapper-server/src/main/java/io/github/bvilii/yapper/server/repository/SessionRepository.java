package io.github.bvilii.yapper.server.repository;

import org.jdbi.v3.core.Jdbi;

import java.time.Instant;
import java.util.Optional;


public class SessionRepository {
    private final Jdbi jdbi;

    public SessionRepository(Jdbi jdbi) {
        this.jdbi = jdbi;
    }


    public void saveSession(String sessionToken, long userId, Instant expiresAt) {
        jdbi.useHandle(handle ->
                handle.createUpdate("INSERT INTO sessions (token, user_id, expires_at) VALUES (:sessionToken, :userId, :expiresAt)")
                        .bind("sessionToken", sessionToken)
                        .bind("userId", userId)
                        .bind("expiresAt", expiresAt)
                        .execute()
        );
    }

    public Optional<String> findUsernameByToken(String token) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT username FROM users JOIN sessions ON users.id = sessions.user_id WHERE token = :token AND sessions.expires_at > CURRENT_TIMESTAMP")
                        .bind("token", token)
                        .mapTo(String.class)
                        .findFirst()
        );
    }


    public void refreshSession(String token, Instant expiresAt) {
        jdbi.useHandle(handle ->
                handle.createUpdate("UPDATE sessions SET expires_at = :expiresAt WHERE token = :token")
                        .bind("expiresAt", expiresAt)
                        .bind("token", token)
                        .execute()
        );
    }
}
