package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Grammar {
    private Set<String> nonTerminals;
    private Set<String> terminals;
    private String startSymbol;
    private Map<List<String>, Set<List<String>>> productions;

    public Grammar(String path) {
        loadFile(path);
    }

    public void loadFile(String path) {
        try {
            Scanner scanner = new Scanner(new File(path));
            this.nonTerminals = new HashSet<>(Arrays.asList(scanner.nextLine().split("\\|")));
            this.terminals = new HashSet<>(Arrays.asList(scanner.nextLine().split("\\|")));
            this.startSymbol = scanner.nextLine();

            this.productions = new HashMap<>();
            while (scanner.hasNextLine()) {
                getProductionFromInput(scanner.nextLine());
            }
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void getProductionFromInput(String input) {
        String[] handSide = input.split("::=");

        List<String> leftSide = Arrays.asList(handSide[0].split(" "));

        String[] rightSide = handSide[1].split("\\|");
        Set<List<String>> rightSideProductions = Arrays.stream(rightSide)
                .map(production -> Arrays.asList(production.split(" ")))
                .collect(Collectors.toSet());

        this.productions.putIfAbsent(leftSide, new HashSet<>());
        this.productions.get(leftSide).addAll(rightSideProductions);
    }

    public boolean checkIfCFG() {
        if (!nonTerminals.contains(startSymbol)) {
            return false;
        }

        for (Map.Entry<List<String>, Set<List<String>>> entry : productions.entrySet()) {
            List<String> leftNonterminal = entry.getKey();

            // Check the left side of the production rule
            if (leftNonterminal.size() != 1 || !nonTerminals.contains(leftNonterminal.get(0))) {
                return false;
            }
        }

        return true;
    }

    public Set<List<String>> getProductionForNonTerminal(String nonTerminal) {
        Set<List<String>> ans = new HashSet<>();
        List<String> key = Collections.singletonList(nonTerminal);

        try {
            Set<List<String>> productionsForNonTerminal = this.getProductions().get(key);
            if (productionsForNonTerminal != null) {
                ans.addAll(productionsForNonTerminal);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return ans;
    }

    public Set<String> getNonTerminals() {
        return nonTerminals;
    }

    public Set<String> getTerminals() {
        return terminals;
    }

    public Map<List<String>, Set<List<String>>> getProductions() {
        return productions;
    }
}
