package org.example;

import java.util.List;
import java.util.Objects;

public class Item {
    private String lhs;
    private List<String> rhs;
    private int dotPosition;

    public Item(String lhs, List<String> rhs, int dotPosition) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.dotPosition = dotPosition;
    }

    public String getLhs() {
        return lhs;
    }

    public List<String> getRhs() {
        return rhs;
    }

    public int getDotPosition() {
        return dotPosition;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return dotPosition == item.dotPosition && Objects.equals(lhs, item.lhs) && Objects.equals(rhs, item.rhs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lhs, rhs, dotPosition);
    }

    @Override
    public String toString() {
        List<String> rhs1 = this.rhs.subList(0, dotPosition);

        String stringrhs1 = String.join("", rhs1);

        List<String> rhs2 = this.rhs.subList(dotPosition, this.rhs.size());

        String stringlhs2 = String.join("", rhs2);

        return lhs.toString() + "->" + stringrhs1 + "." + stringlhs2;
    }
}
