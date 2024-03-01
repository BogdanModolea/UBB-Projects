package org.example;

import java.util.ArrayList;
import java.util.List;

public class TableRow {
    private int index;
    private String info;
    private int parent;
    private int rightSibling;

    public TableRow(int index, String info, int parent, int rightSibling) {
        this.index = index;
        this.info = info;
        this.parent = parent;
        this.rightSibling = rightSibling;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public int getRightSibling() {
        return rightSibling;
    }

    public void setRightSibling(int rightSibling) {
        this.rightSibling = rightSibling;
    }

    @Override
    public String toString() {
        return "index " + index +
                ": info='" + info + '\'' +
                ", parent=" + parent +
                ", rightSibling=" + rightSibling;
    }
}
