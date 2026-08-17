package io.github.bvilii.yapper.shared.model.message;

public class ChatMessage extends Message {
    private String sender;
    private String content;

    public ChatMessage() {
        super(MessageType.CHAT);
    }

    public ChatMessage(String sender, String content) {
        super(MessageType.CHAT);
        this.sender = sender;
        this.content = content;
    }
    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }
}
