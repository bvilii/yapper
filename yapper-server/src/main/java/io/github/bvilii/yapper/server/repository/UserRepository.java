package io.github.bvilii.yapper.server.repository;

import org.jdbi.v3.core.Jdbi;

public class UserRepository {
    private final Jdbi jdbi;

    public UserRepository(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public boolean saveUser(String username, String password) {
        int rows = jdbi.withHandle(handle ->
                handle.createUpdate("INSERT INTO users (username, password) VALUES (:u, :p)")
                        .bind("u", username)
                        .bind("p", password)
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
