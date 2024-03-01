package com.example.chat;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name: ");
        String port = scanner.nextLine();
        ChatClient chatClient = new ChatClient(Integer.parseInt(port));
        Console console = new Console(chatClient);

        console.run();
    }
}

