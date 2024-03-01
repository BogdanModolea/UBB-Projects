package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Grammar {
    private Set<String> nonTerminals;
    private Set<String> terminals;
    private String startSymbol;
    private Map<List<String>, List<List<String>>> productions;
    private boolean isEnchanced;
    private String enchancedStartingSymbol = "S0";

    public Grammar(String path) {
        loadFile(path);
    }

    public Grammar(Set<String> nonTerminals, Set<String> terminals, String startSymbol, Map<List<String>, List<List<String>>> productions) {
        this.nonTerminals = nonTerminals;
        this.terminals = terminals;
        this.startSymbol = startSymbol;
        this.productions = productions;
        this.isEnchanced = true;
    }

    public void loadFile(String path) {
        try {
            Scanner scanner = new Scanner(new File(path));
            this.nonTerminals = new HashSet<>(Arrays.asList(scanner.nextLine().split("\\|")));
            this.terminals = new HashSet<>(Arrays.asList(scanner.nextLine().split("\\|")));
            this.startSymbol = scanner.nextLine();

            this.productions = new LinkedHashMap<>();
            while (scanner.hasNextLine()) {
                getProductionFromInput(scanner.nextLine());
            }

            this.isEnchanced = false;
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

        this.productions.putIfAbsent(leftSide, new ArrayList<>());
        this.productions.get(leftSide).addAll(rightSideProductions);
    }

    public boolean checkIfCFG() {
        if (!nonTerminals.contains(startSymbol)) {
            return false;
        }

        for (List<String> leftHandSide : this.productions.keySet()) {
            if (leftHandSide.size() != 1 || !this.nonTerminals.contains(leftHandSide.get(0))) {
                return false;
            }
        }

        return true;
    }

    public List<List<String>> getProductionForNonTerminal(String nonTerminal) {
        return getProductions().get(Arrays.asList(nonTerminal));
    }

    public Grammar getEnchancedGrammar() {
        if (this.isEnchanced) {
            return this;
        }

        Grammar enchancedGrammar = new Grammar(nonTerminals, terminals, enchancedStartingSymbol, productions);

        enchancedGrammar.nonTerminals.add(enchancedStartingSymbol);
        enchancedGrammar.productions.putIfAbsent(Arrays.asList(enchancedStartingSymbol), new ArrayList<>());

        List<String> newTerminal = new ArrayList<>();
        newTerminal.add(startSymbol);
        enchancedGrammar.productions.get(Arrays.asList(enchancedStartingSymbol)).add(newTerminal);

        return enchancedGrammar;
    }

    public List<Pair<String, List<String>>> getOrderedProductions() {
        List<Pair<String, List<String>>> productionList = new ArrayList<>();
        for (Map.Entry<List<String>, List<List<String>>> entry : productions.entrySet()) {
            for (List<String> production : entry.getValue()) {
                productionList.add(new Pair(entry.getKey().get(0), production));
            }
        }
        return productionList;
    }

    public Set<String> getNonTerminals() {
        return nonTerminals;
    }

    public Set<String> getTerminals() {
        return terminals;
    }

    public Map<List<String>, List<List<String>>> getProductions() {
        return productions;
    }

    public boolean isEnchanced() {
        return isEnchanced;
    }

    public String getStartSymbol() {
        return startSymbol;
    }
}
