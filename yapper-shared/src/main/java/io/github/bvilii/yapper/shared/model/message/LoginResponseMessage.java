package io.github.bvilii.yapper.shared.model.message;

public class LoginResponseMessage extends Message {
    private String message;

    public LoginResponseMessage() {}

    public LoginResponseMessage(MessageType type, String message) {
        super(type);
        this.message = message;
    }
}
