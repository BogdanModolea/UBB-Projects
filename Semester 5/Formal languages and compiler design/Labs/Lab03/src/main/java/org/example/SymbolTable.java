package org.example;

import java.util.ArrayList;

public class SymbolTable {
    private HashTable hashTable;
    private int size;

    public SymbolTable(int size) {
        hashTable = new HashTable(size);
    }

    public HashTable getHashTable(){
        return hashTable;
    }

    public Integer getSize(){
        return hashTable.getSize();
    }

    public Pair getPosition(String symbol){
        return hashTable.getPosition(symbol);
    }

    public boolean containsSymbol(String symbol){
        return hashTable.containsSymbol(symbol);
    }

    public boolean addSymbol(String symbol){
        return hashTable.addSymbol(symbol);
    }

    public boolean removeSymbol(String symbol) {
        return hashTable.removeSymbol(symbol);
    }
}
