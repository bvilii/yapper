package io.github.bvilii.yapper.client.storage;

import java.util.prefs.Preferences;

public class TokenStorage {
    private static final String KEY = "session_token";
    private final Preferences prefs;

    public TokenStorage() {
        this.prefs = Preferences.userNodeForPackage(TokenStorage.class);
    }

    public void saveToken(String token){
        prefs.put(KEY, token);
    }

    public String getToken(){
        return prefs.get(KEY, null);
    }

    public void deleteToken(){
        prefs.remove(KEY);
    }
}
