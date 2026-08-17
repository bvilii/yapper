package io.github.bvilii.yapper.server.repository;

import org.jdbi.v3.core.Jdbi;

public class UserRepository {
    private final Jdbi jdbi;

    public UserRepository(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public boolean saveUser(String username, String password, String password_hash) {
        int rows = jdbi.withHandle(handle ->
                handle.createUpdate("INSERT INTO users (username, password, password_hash) VALUES (:u, :p, :p_h)")
                        .bind("u", username)
                        .bind("p", password)
                        .bind("p_h", password_hash)
                        .execute()
        );
        return rows > 0;
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


}
