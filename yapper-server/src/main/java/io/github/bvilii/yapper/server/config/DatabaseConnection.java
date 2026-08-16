package io.github.bvilii.yapper.server.config;

import org.jdbi.v3.core.Jdbi;

public class DatabaseConnection {
    private final Jdbi jdbi;

    public DatabaseConnection() {
        String host = getEnvOrDefault("DB_HOST", "localhost");
        String port = getEnvOrDefault("DB_PORT", "3306");
        String dbName = getEnvOrDefault("DB_NAME", "yapper_db");
        String user = getEnvOrDefault("DB_USER", "yapper_user");
        String password = getEnvOrDefault("DB_PASSWORD", "localdev123");

        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName
                + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

        this.jdbi = Jdbi.create(url, user, password);
    }

    private static String getEnvOrDefault(String key, String defaultValue) {
        String value = System.getenv(key);
        return (value != null && !value.isBlank()) ? value : defaultValue;
    }

    public Jdbi getJdbi() {
        return jdbi;
    }
}