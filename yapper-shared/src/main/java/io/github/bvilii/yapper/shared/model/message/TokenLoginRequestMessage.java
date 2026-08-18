package io.github.bvilii.yapper.shared.model.message;

public class TokenLoginRequestMessage extends Message{
    private String token;

    public TokenLoginRequestMessage() {
        super(MessageType.TOKEN_LOGIN);
    }

    public TokenLoginRequestMessage(String token) {
        super(MessageType.TOKEN_LOGIN);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
