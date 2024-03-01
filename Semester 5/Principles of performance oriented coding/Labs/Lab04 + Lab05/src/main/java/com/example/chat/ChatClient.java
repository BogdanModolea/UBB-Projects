package com.example.chat;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatClient {
    private final ExecutorService executor = Executors.newFixedThreadPool(5);
    private final List<Chat> chats = new ArrayList<>();
    private final ServerSocket ss;
    private final Scanner scanner = new Scanner(System.in);

    public ChatClient(int clientPort) throws IOException {
        this.ss = new ServerSocket(clientPort);
    }

    public void start() {
        executor.submit(() -> {
            while (!this.ss.isClosed()) {
                try {
                    Socket socket = ss.accept();
                    handleNewChat(socket);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void handleNewChat(Socket socket) throws IOException {
        System.out.println("Received connection request from " + socket.getPort());
        Chat chat = createAndInitializeChat(socket);

        if (isAcknowledged()) {
            sendAcknowledgement(chat);
        } else {
            sendRefusalAndCloseChat(chat);
        }
    }

    private Chat createAndInitializeChat(Socket socket) throws IOException {
        Chat chat = new Chat(socket);

        synchronized (chats) {
            chats.add(chat);
            chat.listenForMessages();
        }

        return chat;
    }

    private boolean isAcknowledged() {
        System.out.print("Enter command (e.g., !ack): ");
        String cmd = scanner.nextLine().trim();
        return cmd.equals("!ack");
    }

    private void sendAcknowledgement(Chat chat) throws IOException {
        System.out.println("Sending acknowledgement...");
        JsonObject jsonObject = createMessageJson(MessageType.ACK, "");
        chat.sendMessage(new Message(jsonObject));
    }

    private void sendRefusalAndCloseChat(Chat chat) throws IOException {
        System.out.println("Sending refusal...");
        JsonObject jsonObject = createMessageJson(MessageType.BYE, "");
        chat.sendMessage(new Message(jsonObject));
        chat.closeChat();
    }

    private JsonObject createMessageJson(MessageType messageType, String messageContent) {
        return Json.createObjectBuilder()
                .add("message", messageContent)
                .add("type", messageType.toString())
                .build();
    }


    private boolean isConnected(int port) {
        return this.chats.stream()
                .anyMatch((f) -> f.getSocket().getPort() == port);
    }

    public String connectToFriend(String host, int port) {
        try {
            synchronized (chats) {
                if (isConnected(port)) {
                    return "Already connected to " + port;
                }
            }

            System.out.println("Connecting to " + port + "...");
            Socket socket = new Socket(host, port);

            return connectAndHandleResponse(socket);
        } catch (Exception e) {
            return "Connection refused";
        }
    }

    private String connectAndHandleResponse(Socket socket) {
        try {
            return executor.submit(() -> {
                try {
                    Chat chat = new Chat(socket);
                    System.out.println("Waiting for ack...");
                    Message message = chat.receiveMessage();

                    if (message.getType() == MessageType.ACK) {
                        handleSuccessfulConnection(chat);
                        return "Connection successful";
                    } else {
                        return "Connection refused";
                    }
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }).get();
        } catch (Exception e) {
            return "Connection refused";
        }
    }

    private void handleSuccessfulConnection(Chat chat) throws IOException {
        synchronized (chats) {
            chats.add(chat);
            chat.listenForMessages();
        }
    }

    public List<Chat> getChats() {
        return chats;
    }

    public Chat makeChatActive(int port) {
        Chat chat = this.chats.stream()
                .filter((c) -> c.getSocket().getPort() == port)
                .findFirst().orElseThrow();

        chat.setActive(true);

        return chat;
    }

    public void makeChatInactive() {
        this.chats.stream()
                .filter(Chat::isActive)
                .findFirst()
                .ifPresent(currentActive -> currentActive.setActive(false));
    }

    public void closeConnection(int port) throws IOException {
        Chat chat = this.chats.stream()
                .filter((c) -> c.getSocket().getPort() == port)
                .findFirst().orElseThrow();
        chat.closeChat();
        this.chats.remove(chat);
    }

    public void removeChat(Chat chat) {
        this.chats.remove(chat);
    }

    public void closeAllConnections() throws IOException {
        this.chats.forEach(chat -> {
            try {
                JsonObject jsonObject = createMessageJson(MessageType.BYE, "");
                chat.sendMessage(new Message(jsonObject));
                chat.closeChat();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        this.executor.shutdown();
        this.ss.close();
    }
}


