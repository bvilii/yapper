package io.github.bvilii.yapper.shared.model.message;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = LoginRequestMessage.class, name = "LOGIN"),
        @JsonSubTypes.Type(value = LoginResponseMessage.class, name = "LOGIN_SUCCESS"),
        @JsonSubTypes.Type(value = LoginResponseMessage.class, name = "LOGIN_ERROR"),
        @JsonSubTypes.Type(value = RegisterRequestMessage.class, name = "REGISTER"),
        @JsonSubTypes.Type(value = RegisterResponseMessage.class, name = "REGISTER_SUCCESS"),
        @JsonSubTypes.Type(value = RegisterResponseMessage.class, name = "REGISTER_ERROR"),
        @JsonSubTypes.Type(value = ChatMessage.class, name = "CHAT"),
        @JsonSubTypes.Type(value = TokenLoginRequestMessage.class, name = "TOKEN_LOGIN"),
        @JsonSubTypes.Type(value = TokenLoginResponseMessage.class, name = "TOKEN_LOGIN_SUCCESS"),
        @JsonSubTypes.Type(value = TokenLoginResponseMessage.class, name = "TOKEN_LOGIN_ERROR")
})
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
