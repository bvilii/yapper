package io.github.bvilii.yapper.shared.model.message;

public class RegisterRequestMessage extends Message {
    private String username;
    private String password;

    public RegisterRequestMessage(){
        super(MessageType.REGISTER);
    }

    public RegisterRequestMessage(String username, String password){
        super(MessageType.REGISTER);
        this.username = username;
        this.password = password;
    }
}
