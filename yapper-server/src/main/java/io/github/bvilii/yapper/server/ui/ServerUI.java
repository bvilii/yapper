package io.github.bvilii.yapper.server.ui;

import io.github.bvilii.yapper.server.network.ClientSession;

import java.io.IOException;

public class ServerUI implements ServerListener, ClientLifecycleListener{


    @Override
    public void onClientConnected(ClientSession session) {
        display("Client connected!");
    }

    @Override
    public void onClientDisconnected(ClientSession session) {
        display("Client disconnected!");
    }

    @Override
    public void onClientSetupFailed(Exception e) {
        display("Client setup failed! Error: " + e.getMessage());
    }

    @Override
    public void onClientConnectionError(ClientSession session, IOException e) {
        display("Client connection error! Error: " + e.getMessage());
    }

    @Override
    public void onServerStart() {
        display("Server started!");
    }

    @Override
    public void onServerStartError(IOException e) {
        display("Server starting ran into an error! Error: " + e.getMessage());
    }

    public void display(String message){
        System.out.println(message);
    }

}
