package io.github.bvilii.yapper.shared.model.message;

public class LoginRequestMessage extends Message{
    private String username;
    private String password;

    public LoginRequestMessage() {
        super(MessageType.LOGIN);
    }

    public LoginRequestMessage(String username, String password) {
        super(MessageType.LOGIN);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
