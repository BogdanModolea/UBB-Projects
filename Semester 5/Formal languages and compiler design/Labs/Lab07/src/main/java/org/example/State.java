package org.example;

import java.util.*;

public class State {
    Set<Item> itemSet;
    Action stateAction;

    public State(Set<Item> itemSet) {
        this.itemSet = itemSet;
        this.setActionForState();
    }

    public Set<Item> getItemSet() {
        return itemSet;
    }

    public Action getStateAction() {
        return stateAction;
    }

    public List<String> getSymbolsSucceedingTheDot(){
        Set<String> symbols = new HashSet<>();

        for(Item item: itemSet){
            if(item.getDotPosition() < item.getRhs().size())
                symbols.add(item.getRhs().get(item.getDotPosition()));
        }

        return new ArrayList<>(symbols);
    }

    public void setActionForState() {
        if (itemSet.size() == 1 && ((Item) itemSet.toArray()[0]).getRhs().size() == ((Item) itemSet.toArray()[0]).getDotPosition() && Objects.equals(((Item) this.itemSet.toArray()[0]).getLhs(), "S0")) {
            this.stateAction = Action.ACCEPT;
        } else if (itemSet.size() == 1 && ((Item) itemSet.toArray()[0]).getRhs().size() == ((Item) itemSet.toArray()[0]).getDotPosition()) {
            this.stateAction = Action.REDUCE;
        } else if (!itemSet.isEmpty() && this.itemSet.stream().allMatch(i -> i.getRhs().size() > i.getDotPosition())) {
            this.stateAction = Action.SHIFT;
        } else if (itemSet.size() > 1 && this.itemSet.stream().allMatch(i -> i.getRhs().size() == i.getDotPosition())) {
            this.stateAction = Action.REDUCE_REDUCE_CONFLICT;
        } else {
            this.stateAction = Action.SHIFT_REDUCE_CONFLICT;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(itemSet, state.itemSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemSet);
    }

    @Override
    public String toString() {
        return itemSet.toString();
    }
}
