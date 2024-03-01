package org.example;

import java.util.Scanner;

public class GrammarMain {
    public static void printMenu() {
        System.out.println("1. Print non-terminals");
        System.out.println("2. Print terminals");
        System.out.println("3. Print all productions");
        System.out.println("4. Print all productions for a non terminal");
        System.out.println("5. Is the grammar a context free grammar (CFG) ?");
        System.out.println("0. Exit");
        System.out.println();
    }

    public static void runGrammar() {
        Grammar grammar = new Grammar("grammar/g2.txt");
        Scanner scanner = new Scanner(System.in);

        int option;
        while (true) {
            printMenu();
            System.out.print("Your option: ");
            option = scanner.nextInt();
            scanner.nextLine();

            if (option == 0) {
                System.out.println("Exiting...");
                break;
            } else if (option == 1) {
                System.out.println(grammar.getNonTerminals());
            } else if (option == 2) {
                System.out.println(grammar.getTerminals());
            } else if (option == 3) {
                System.out.println(grammar.getProductions());
            } else if (option == 4) {
                System.out.print("Non terminal: ");
                String nonTerminal = scanner.nextLine();
                System.out.println(grammar.getProductionForNonTerminal(nonTerminal));
            } else if (option == 5) {
                System.out.println(grammar.checkIfCFG());
            } else {
                System.out.println("Wrong input!");
            }
        }
    }

    public static void main(String[] args) {
        runGrammar();
    }
}
