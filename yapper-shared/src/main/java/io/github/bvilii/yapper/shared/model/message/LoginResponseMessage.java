package io.github.bvilii.yapper.shared.model.message;

public class LoginResponseMessage extends Message {
    private String message;
    private String token;

    public LoginResponseMessage() {}

    public LoginResponseMessage(MessageType type, String message,  String token) {
        super(type);
        this.message = message;
        this.token = token;
    }

    public String getMessage() {
        return message;
    }
}
