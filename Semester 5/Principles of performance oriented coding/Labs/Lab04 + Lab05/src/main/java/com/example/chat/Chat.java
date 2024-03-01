package com.example.chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Chat {
    Socket socket;
    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;
    List<Message> messages = new ArrayList<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(1);
    boolean active = false;
    boolean done = false;

    public Chat(Socket socket) throws IOException {
        this.socket = socket;
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
    }

    public void sendMessage(Message message) throws IOException {
        if(this.socket.isClosed()) {
            System.out.println("Chat is closed. Type !back to go back to the menu");
        } else {
            outputStream.writeObject(message);
        }
    }

    public Message receiveMessage() throws IOException, ClassNotFoundException {
        return (Message) inputStream.readObject();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void listenForMessages() {
        executor.submit(() -> {
            while (isListening()) {
                try {
                    Message message = receiveMessage();

                    if (message.getType() == MessageType.BYE) {
                        handleByeMessage();
                    } else {
                        processNonByeMessage(message);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private boolean isListening() {
        return !done && !socket.isClosed();
    }

    private void handleByeMessage() throws IOException {
        done = true;
        socket.close();
        System.out.println(socket.getPort() + " closed the connection.");
    }

    private void processNonByeMessage(Message message) {
        if (isActive()) {
            System.out.println(socket.getPort() + ": " + message.getMessage());
        } else {
            messages.add(message);
        }
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Socket getSocket() {
        return socket;
    }

    public void closeChat() throws IOException {
        this.socket.close();
        this.executor.shutdown();
    }
}


