package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class GrammarMain {
    public static void printMenu() {
        System.out.println("\n1. Print non-terminals");
        System.out.println("2. Print terminals");
        System.out.println("3. Print all productions");
        System.out.println("4. Print all productions for a non terminal");
        System.out.println("5. Is the grammar a context free grammar (CFG) ?");
        System.out.println("6. Parsing table for the grammar");
        System.out.println("7. Parse a sequence");
        System.out.println("0. Exit");
        System.out.println();
    }

    public static void runGrammar() {
        Grammar grammar = new Grammar("grammar/g3.txt");
        Scanner scanner = new Scanner(System.in);
        Grammar parserGrammar = new Grammar("grammar/g3.txt");
        Parser parser = new Parser(parserGrammar);


        int option;
        while (true) {
            printMenu();
            System.out.print("Your option: ");
            option = scanner.nextInt();
            scanner.nextLine();
            try {
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
                } else if (option == 6) {
                    System.out.println(parser.getParsingTable());
                    writeToFile("grammar/parser_out1.txt", parser.getParsingTable().toString());
                } else if (option == 7) {
                    Scanner inFileRead = new Scanner(new File("grammar/seq.txt"));
                    List<String> sendWord = new ArrayList<>();
                    while (inFileRead.hasNextLine()) {
                        String line = inFileRead.nextLine();
                        sendWord.add(line);
                    }
                    List<TableRow> table = parser.parse(sendWord);
                    Collections.sort(table, Comparator.comparing(TableRow::getIndex));

                    StringBuilder sb = new StringBuilder();
                    for (TableRow row : table) {
                        System.out.println(row);
                        sb.append(row.toString() + "\n");
                    }
                    writeToFile("grammar/out1.txt", sb.toString());

                } else {
                    System.out.println("Wrong input!");
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void writeToFile(String fileName, String content) {
        try {
            Formatter formatter = new Formatter(fileName);
            formatter.format("%s", content);
            formatter.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        runGrammar();
    }
}
