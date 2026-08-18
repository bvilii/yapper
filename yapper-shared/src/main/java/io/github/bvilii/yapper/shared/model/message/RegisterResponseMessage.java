package io.github.bvilii.yapper.shared.model.message;

public class RegisterResponseMessage extends Message{
    private String message;
    private String token;

    public RegisterResponseMessage() {}

    public RegisterResponseMessage(MessageType type, String message,  String token) {
        super(type);
        this.message = message;
        this.token = token;
    }
}
