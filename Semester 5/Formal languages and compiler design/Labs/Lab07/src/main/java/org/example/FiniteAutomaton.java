package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FiniteAutomaton {
    private List<String> states;
    private List<String> alphabet;
    private final Map<Pair<String, String>, Set<String>> transitions;
    private String initialState;
    private List<String> finalState;


    public FiniteAutomaton(String filePath) {
        this.transitions = new HashMap<>();
        init(filePath);
    }

    public List<String> getStates() {
        return states;
    }

    public List<String> getAlphabet() {
        return alphabet;
    }

    public String getInitialState() {
        return initialState;
    }

    public List<String> getFinalState() {
        return finalState;
    }

    public void init(String filePath) {
        try {
            Scanner scanner = new Scanner(new File(filePath));
            this.states = new ArrayList<>(Arrays.asList(scanner.nextLine().split(",")));
            this.alphabet = new ArrayList<>(Arrays.asList(scanner.nextLine().split(",")));
            this.initialState = scanner.nextLine();
            this.finalState = new ArrayList<>(Arrays.asList(scanner.nextLine().split(",")));

            while (scanner.hasNextLine()) {
                String transition = scanner.nextLine();
                String[] transitionComponents = transition.split(" ");

                String fromState = transitionComponents[0];
                String toState = transitionComponents[1];
                String symbol = transitionComponents[2];


                if (isValidTransition(fromState, toState, symbol)) {
                    Pair<String, String> transitionKey = new Pair<>(fromState, symbol);
                    transitions.computeIfAbsent(transitionKey, k -> new HashSet<>()).add(toState);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidTransition(String fromState, String toState, String symbol) {
        return states.contains(fromState) && states.contains(toState) && alphabet.contains(symbol);
    }

    public boolean isDeterministic() {
        for (Pair<String, String> p : transitions.keySet()) {
            if (transitions.get(p).size() > 1) {
                return false;
            }
        }
        return true;
    }


    public String writeTransitions() {
        StringBuilder stringBuilder = new StringBuilder();
        transitions.forEach((key, value) -> stringBuilder.append(key.getFirst())
                .append(" -(")
                .append(key.getSecond())
                .append(")-> ")
                .append(value)
                .append("\n"));
        return stringBuilder.toString();
    }


    public boolean checkSequence(String sequence) {
        if (sequence.isEmpty()) {
            return finalState.contains(initialState);
        }

        String currentState = initialState;
        for (char symbol : sequence.toCharArray()) {
            Pair<String, String> transitionKey = new Pair<>(currentState, String.valueOf(symbol));
            Set<String> possibleNextStates = transitions.get(transitionKey);
            if (possibleNextStates == null || possibleNextStates.isEmpty()) {
                return false;
            }
            currentState = possibleNextStates.iterator().next();
        }

        return finalState.contains(currentState);
    }
}
