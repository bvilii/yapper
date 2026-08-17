package io.github.bvilii.yapper.shared.model.message;

public class RegisterResponseMessage extends Message{
    private boolean success;
    private String message;

    public RegisterResponseMessage() {}

    public RegisterResponseMessage(MessageType type, String message) {
        super(type);
        this.message = message;
    }
}
