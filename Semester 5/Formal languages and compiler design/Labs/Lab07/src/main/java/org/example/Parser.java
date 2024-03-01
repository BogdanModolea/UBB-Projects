package org.example;

import java.util.*;

public class Parser {
    Grammar grammar;
    Grammar enhancedGrammar;
    private List<Pair<String, List<String>>> orderedProductions;

    public Parser(Grammar grammar) {
        this.grammar = grammar;

        if(this.grammar.isEnchanced()) {
            this.enhancedGrammar = this.grammar;
        }
        else {
            this.enhancedGrammar = this.grammar.getEnchancedGrammar();
        }

        this.orderedProductions = enhancedGrammar.getOrderedProductions();
    }

    public String getNonTerminalPrecededByDot(Item item){
        try {
            String term = item.getRhs().get(item.getDotPosition());
            if(!grammar.getNonTerminals().contains(term)){
                return null;
            }

            return term;
        }
        catch (Exception e){
            return null;
        }

    }

    public State closure(Item initialItem) {
        Set<Item> currentClosure = new HashSet<>();
        Set<Item> oldClosure;
        currentClosure.add(initialItem);

        do {
            oldClosure = currentClosure;
            Set<Item> updatedClosure = new HashSet<>(oldClosure);

            // Iterate through items in the current closure
            for (Item currentItem : currentClosure) {
                // Retrieve the non-terminal preceding the dot
                String nonTerminal = getNonTerminalPrecededByDot(currentItem);

                // If there is a non-terminal, add items from its productions to the closure
                if (nonTerminal != null) {
                    for (List<String> rhs : grammar.getProductionForNonTerminal(nonTerminal)) {
                        Item newItem = new Item(nonTerminal, rhs, 0);
                        updatedClosure.add(newItem);
                    }
                }
            }

            // Update the current closure with the new unique items
            currentClosure = updatedClosure;

            // Continue the loop until no new items are added
        } while (!oldClosure.equals(currentClosure));

        // Return a new state with the final closure items
        return new State(currentClosure);
    }


    public State goTo(State currentState, String symbol) {
        // Create a set to store the resulting items
        Set<Item> resultingItems = new HashSet<>();

        // Iterate through items in the current state
        for (Item currentItem : currentState.getItemSet()) {
            // Retrieve the non-terminal at the current dot position
            String nonTerminal = currentItem.getRhs().get(currentItem.getDotPosition());

            // Check if the non-terminal matches the specified symbol
            if (Objects.equals(nonTerminal, symbol)) {
                // Create the next item by advancing the dot position
                Item nextItem = new Item(currentItem.getLhs(), currentItem.getRhs(), currentItem.getDotPosition() + 1);

                // Compute the closure of the next item and add the resulting items to the set
                State newState = closure(nextItem);
                resultingItems.addAll(newState.getItemSet());
            }
        }

        // Create and return a new state with the resulting items
        return new State(resultingItems);
    }



    public CanonicalCollection canonicalCollection() {
        // Create a new CanonicalCollection
        CanonicalCollection canonicalCollection = new CanonicalCollection();

        // Initialize the canonical collection with the closure of the initial item
        canonicalCollection.addState(closure(createInitialItem()));

        // Iterate through the states in the canonical collection
        for (int index = 0; index < canonicalCollection.getStates().size(); index++)
            // Explore symbols succeeding the dot in the current state
            for (String symbol : canonicalCollection.getStates().get(index).getSymbolsSucceedingTheDot()) {
                // Compute the new state using the goTo function
                State newState = goTo(canonicalCollection.getStates().get(index), symbol);

                // Check if the new state is not empty and has not been added to the collection
                if (!newState.getItemSet().isEmpty() && !canonicalCollection.getStates().contains(newState)) {
                    // Add the new state to the canonical collection
                    canonicalCollection.addState(newState);
                }

                canonicalCollection.connectStates(index, symbol, canonicalCollection.getStates().indexOf(newState));
            }

        // Return the generated CanonicalCollection
        return canonicalCollection;
    }

    // Helper method to create the initial item for the start symbol
    private Item createInitialItem() {
        return new Item(
                enhancedGrammar.getStartSymbol(),
                enhancedGrammar.getProductionForNonTerminal(enhancedGrammar.getStartSymbol()).get(0),
                0
        );
    }

    public ParsingTable getParsingTable() {
        CanonicalCollection canonicalCollection = canonicalCollection();
        ParsingTable table = new ParsingTable();

        for (Map.Entry<Pair<Integer, String>, Integer> entry : canonicalCollection.getAdjacencyList().entrySet()) {
            Pair<Integer, String> key = entry.getKey();
            Integer stateIndex = key.getFirst();
            State state = canonicalCollection.getStates().get(stateIndex);

            if (!table.getTableRow().containsKey(stateIndex)) {
                table.getTableRow().put(stateIndex, new Row(state.getStateAction(), new HashMap<>(), null));
            }

            table.getTableRow().get(stateIndex).getGoto().put(key.getSecond(), entry.getValue());
        }

        for (int index = 0; index < canonicalCollection.getStates().size(); index++) {
            State state = canonicalCollection.getStates().get(index);

            if (state.getStateAction() == Action.REDUCE) {
                Item firstItem = state.getItemSet().stream().findFirst().get();
                int productionIndex = orderedProductions.indexOf(new Pair<>(firstItem.getLhs(), firstItem.getRhs()));
                table.getTableRow().put(index, new Row(state.getStateAction(), null, productionIndex));
            }

            if (state.getStateAction() == Action.ACCEPT) {
                table.getTableRow().put(index, new Row(state.getStateAction(), null, null));
            }
        }

        return table;
    }

    public List<TableRow> parse(List<String> word) throws Exception {
        List<Pair<String, Integer>> workingStack = new ArrayList<>();
        List<String> remainingStack = new ArrayList<>(word);
        List<Integer> productionStack = new ArrayList<>();
        ParsingTable parsingTable = getParsingTable();
        workingStack.add(new Pair("$", 0));

        List<TableRow> parsingTree = new ArrayList<>();
        List<Pair<String, Integer>> treeStack = new ArrayList<>();
        int currentIndex = 0;

        while (!remainingStack.isEmpty() || !workingStack.isEmpty()) {
            Row tableValue = parsingTable.getTableRow().get(workingStack.get(workingStack.size() - 1).getSecond());
            if (tableValue == null) {
                throw new Exception("Invalid state " + workingStack.get(workingStack.size() - 1).getSecond() + " in the working stack");
            }

            switch (tableValue.getAction()) {
                case SHIFT:
                    if (remainingStack.isEmpty()) {
                        throw new Exception("Action is shift but nothing else is left in the remaining stack");
                    }
                    String token = remainingStack.get(0);
                    if (!tableValue.getGoto().containsKey(token)) {
                        throw new Exception("Invalid symbol \"" + token + "\" for goto of state " + workingStack.get(workingStack.size() - 1).getSecond());
                    }
                    workingStack.add(new Pair(token, tableValue.getGoto().get(token)));
                    remainingStack.remove(0);
                    treeStack.add(new Pair(token, currentIndex++));
                    break;

                case ACCEPT:
                    Pair<String, Integer> lastElement = treeStack.remove(treeStack.size() - 1);
                    parsingTree.add(new TableRow(lastElement.getSecond(), lastElement.getFirst(), -1, -1));
                    return parsingTree;

                case REDUCE:
                    Pair<String, List<String>> productionToReduceTo = orderedProductions.get(tableValue.getReductionIndex());
                    int parentIndex = currentIndex++;
                    int lastIndex = -1;
                    for (int j = 0; j < productionToReduceTo.getSecond().size(); j++) {
                        workingStack.remove(workingStack.size() - 1);
                        Pair<String, Integer> removedElement = treeStack.remove(treeStack.size() - 1);
                        parsingTree.add(new TableRow(removedElement.getSecond(), removedElement.getFirst(), parentIndex, lastIndex));
                        lastIndex = removedElement.getSecond();
                    }
                    treeStack.add(new Pair(productionToReduceTo.getFirst(), parentIndex));
                    Pair<String, Integer> previous = workingStack.get(workingStack.size() - 1);
                    workingStack.add(new Pair(productionToReduceTo.getFirst(), parsingTable.getTableRow().get(previous.getSecond()).getGoto().get(productionToReduceTo.getFirst())));
                    productionStack.add(0, tableValue.getReductionIndex());
                    break;

                default:
                    throw new Exception(tableValue.getAction().toString());
            }
        }
        throw new Exception("Something went wrong!");
    }


    public Grammar getGrammar() {
        return grammar;
    }

    public Grammar getEnhancedGrammar() {
        return enhancedGrammar;
    }
}
