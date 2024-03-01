package org.example;

import java.util.Map;

public class Row {
    private Action action;
    private Map<String, Integer> goTo;
    private Integer reductionIndex;

    public Row(Action action, Map<String, Integer> goTo, Integer reductionIndex) {
        this.action = action;
        this.goTo = goTo;
        this.reductionIndex = reductionIndex;
    }

    public Action getAction() {
        return action;
    }

    public Map<String, Integer> getGoto() {
        return goTo;
    }

    public Integer getReductionIndex() {
        return reductionIndex;
    }

    @Override
    public String toString() {
        switch (action) {
            case REDUCE:
                return "REDUCE " + reductionIndex;
            case ACCEPT:
                return "ACCEPT";
            case SHIFT:
                return "SHIFT " + goTo;
            default:
                throw new IllegalStateException("No other states allowed");
        }
    }
}
