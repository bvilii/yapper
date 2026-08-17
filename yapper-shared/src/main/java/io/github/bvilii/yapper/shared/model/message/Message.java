package io.github.bvilii.yapper.shared.model.message;


public abstract class Message {
    private MessageType type;

    public Message(){}

    public Message(MessageType type) {
        this.type = type;
    }

    public MessageType getType() {
        return type;
    }
}
