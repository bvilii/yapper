package io.github.bvilii.yapper.shared.model.message;

public class TokenLoginResponseMessage extends Message {
    private String username;

    public TokenLoginResponseMessage(){
    }

    public TokenLoginResponseMessage(MessageType type, String username){
        super(type);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
