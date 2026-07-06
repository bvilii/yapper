package io.github.bvilii.yapper.shared.model;


public class Message {
    private String content;
    private String sender;
    private MessageType type;

    public Message(){}

    public Message(String content, String sender, MessageType type) {
        this.content = content;
        this.sender = sender;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }

    public MessageType getType() {
        return type;
    }
}
