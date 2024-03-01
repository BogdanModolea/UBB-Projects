package org.example;

import java.util.*;

public class CanonicalCollection {
    private List<State> stateList;
    private Map<Pair<Integer, String>, Integer> adjacencyList;

    public CanonicalCollection() {
        this.stateList = new ArrayList<>();
        this.adjacencyList = new LinkedHashMap<>();
    }

    public void addState(State state) {
        this.stateList.add(state);
    }

    public void connectStates(int indexFirstState, String symbol, int indexSecondState) {
        this.adjacencyList.put(new Pair<>(indexFirstState, symbol), indexSecondState);
    }

    public List<State> getStates() {
        return this.stateList;
    }

    public Map<Pair<Integer, String>, Integer> getAdjacencyList() {
        return this.adjacencyList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CanonicalCollection that = (CanonicalCollection) o;
        return Objects.equals(stateList, that.stateList) && Objects.equals(adjacencyList, that.adjacencyList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stateList, adjacencyList);
    }

    @Override
    public String toString() {
        return "CanonicalCollection{" +
                "stateList=" + stateList +
                ", adjacencyList=" + adjacencyList +
                '}';
    }
}
