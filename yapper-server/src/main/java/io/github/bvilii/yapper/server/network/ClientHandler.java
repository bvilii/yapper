package io.github.bvilii.yapper.server.network;

import com.google.gson.Gson;
import io.github.bvilii.yapper.server.model.Message;
import io.github.bvilii.yapper.server.ui.ClientHandlerListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private ClientHandlerListener listener;
    private Gson gson;

    public ClientHandler(Socket clientSocket, ClientHandlerListener listener) {
        this.clientSocket = clientSocket;
        this.listener = listener;
        this.gson = new Gson();
    }


    @Override
    public void run() {
        try (BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            listener.onClientConnected();

            String message;
            while ((message = inputFromClient.readLine()) != null) {
                Message msg = gson.fromJson(message, Message.class);

            }

        } catch (IOException e) {
            listener.onClientConnectionError(e);
        }


    }
}