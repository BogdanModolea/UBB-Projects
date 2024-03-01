package org.example;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    private static void printMenu() {
        System.out.println("1. Print states");
        System.out.println("2. Print alphabet");
        System.out.println("3. Print transitions");
        System.out.println("4. Print initial state");
        System.out.println("5. Print final states");
        System.out.println("6. Check if DFA is accepted by FA");
        System.out.println("7. Check if sequence is accepted by FA");
        System.out.println("0. Exit");
    }

    public static void menuForDFA() {
        FiniteAutomaton finiteAutomaton = new FiniteAutomaton("FA.in");
        Scanner scanner = new Scanner(System.in);

        System.out.println("FA read from file.");

        int option;
        while (true) {
            printMenu();
            System.out.print("Your option: ");
            option = scanner.nextInt();
            scanner.nextLine();

            if (option == 1) {
                System.out.println("States: ");
                System.out.println(finiteAutomaton.getStates());
            } else if (option == 2) {
                System.out.println("Alphabet: ");
                System.out.println(finiteAutomaton.getAlphabet());
            } else if (option == 3) {
                System.out.println(finiteAutomaton.writeTransitions());
            } else if (option == 4) {
                System.out.println("Initial state: ");
                System.out.println(finiteAutomaton.getInitialState());
            } else if (option == 5) {
                System.out.println("Final states: ");
                System.out.println(finiteAutomaton.getFinalState());
            } else if(option == 6) {
                System.out.println("Is DFA: ");
                if (finiteAutomaton.isDeterministic()) {
                    System.out.println("Accepted by FA");
                } else {
                    System.out.println("FA doesn't accept it");
                }
            }
            else if (option == 7) {
                System.out.print("Your sequence: ");
                String sequence = scanner.nextLine();
                if (finiteAutomaton.checkSequence(sequence)) {
                    System.out.println("Sequence is valid");
                } else {
                    System.out.println("Invalid sequence");
                }
            } else if (option == 0) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid command!");
            }

            System.out.println();
        }
    }

    public static void main(String[] args) {
        menuForDFA();
    }
}