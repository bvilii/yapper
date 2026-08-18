package io.github.bvilii.yapper.server.repository;

import org.jdbi.v3.core.Jdbi;
import java.util.Optional;

public class UserRepository {
    private final Jdbi jdbi;

    public UserRepository(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public long saveUser(String username, String password_hash) {
        return jdbi.withHandle(handle ->
                handle.createUpdate("INSERT INTO users (username, password_hash) VALUES (:u, :p_hash)")
                        .bind("u", username)
                        .bind("p_hash", password_hash)
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(Long.class)
                        .one()
        );
    }

    public boolean existsByUsername(String username) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT 1 FROM users WHERE username = :u")
                        .bind("u", username)
                        .mapTo(Integer.class)
                        .findFirst()
                        .isPresent()
        );
    }

    public Optional<String> findPasswordHashByUsername(String username) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT password_hash FROM users WHERE username = :u")
                        .bind("u", username)
                        .mapTo(String.class)
                        .findFirst()
        );
    }

    public Optional<Long> findIdByUsername(String username) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT id FROM users WHERE username = :u")
                .bind("u", username)
                .mapTo(Long.class)
                .findFirst()
        );
    }

}
