package org.example;

import java.util.Map;
import java.util.TreeMap;

public class ParsingTable {
    private Map<Integer, Row> tableRow;

    public ParsingTable() {
        this.tableRow = new TreeMap<>(); // TreeMap is used to maintain the order of keys
    }

    public Map<Integer, Row> getTableRow() {
        return tableRow;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (Map.Entry<Integer, Row> entry : tableRow.entrySet()) {
            string.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return string.toString();
    }
}
