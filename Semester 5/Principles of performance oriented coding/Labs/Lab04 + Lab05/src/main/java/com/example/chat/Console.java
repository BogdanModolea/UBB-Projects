package com.example.chat;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class Console {
    private final ChatClient chatClient;
    private final Scanner scanner = new Scanner(System.in);

    public Console(ChatClient chatClient) throws IOException {
        this.chatClient = chatClient;
        chatClient.start();
    }

    private void printCommands() {
        System.out.println("""
                COMMANDS:\s
                !hello <port> - connect to port\s
                !friends - show connected friends\s
                !chat <port> - enter chat with a friend\s
                !byebye - log out\s
                """);
    }

    public void run() throws IOException, ExecutionException, InterruptedException {
        System.out.println("Use !commands for a list of commands\n");
        boolean done = false;

        while (!done) {
            String cmd = scanner.nextLine().trim();
            List<String> args = Arrays.stream(cmd.split(" ")).toList();

            String command = args.get(0);

            if ("!commands".equals(command)) {
                printCommands();
            } else if ("!hello".equals(command)) {
                connectToFriend(Integer.parseInt(args.get(1)));
            } else if ("!byebye".equals(command)) {
                done = true;
            } else if ("!friends".equals(command)) {
                listFriends();
            } else if ("!chat".equals(command)) {
                handleChat(Integer.parseInt(args.get(1)));
            } else {
                System.out.println("Unknown command");
            }
        }

        this.chatClient.closeAllConnections();
    }

    private void connectToFriend(int port) {
        System.out.println(chatClient.connectToFriend("127.0.0.1", port));
    }

    private void listFriends() {
        this.chatClient.getChats()
                .forEach((chat) -> System.out.println(chat.getSocket().getPort()));

        if (this.chatClient.getChats().isEmpty()) {
            System.out.println("No friends available");
        }
    }

    private void handleChat(int port) {
        try {
            Chat chat = chatClient.makeChatActive(port);

            printUnreadMessages(chat, port);
            System.out.println("You are now chatting with " + port + ". Type !back to go back to the menu");

            boolean done = false;

            while (!done) {
                String message = scanner.nextLine();

                if (Objects.equals(message, "!back")) {
                    handleBackCommand(chat);
                    done = true;
                } else if (Objects.equals(message, "!bye") && !chat.getSocket().isClosed()) {
                    handleByeCommand(chat, port);
                    done = true;
                } else {
                    handleRegularMessage(chat, message);
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("No such chat found.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void printUnreadMessages(Chat chat, int port) {
        List<Message> unread = chat.getMessages();
        if (!unread.isEmpty()) {
            System.out.println("Unread messages: ");
            unread.forEach(m -> System.out.println(port + ": " + m.getMessage()));
            chat.setMessages(new ArrayList<>());
        }
    }

    private void handleBackCommand(Chat chat) throws IOException {
        if (chat.getSocket().isClosed()) {
            chatClient.removeChat(chat);
        } else {
            chatClient.makeChatInactive();
        }
        System.out.println("Went back to the menu.");
    }

    private void handleByeCommand(Chat chat, int port) throws IOException {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("message", "")
                .add("type", MessageType.BYE.toString())
                .build();
        chat.sendMessage(new Message(jsonObject));
        chatClient.closeConnection(port);
    }

    private void handleRegularMessage(Chat chat, String message) throws IOException {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("message", message)
                .add("type", MessageType.MESSAGE.toString())
                .build();
        chat.sendMessage(new Message(jsonObject));
    }
}
