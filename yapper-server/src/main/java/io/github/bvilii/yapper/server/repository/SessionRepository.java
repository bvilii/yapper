package io.github.bvilii.yapper.server.repository;

import org.jdbi.v3.core.Jdbi;

import java.time.Instant;


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


}
