package org.example;

import java.util.ArrayList;

public class HashTable {
    private int size;
    private ArrayList<ArrayList<String>> symbols;

    public HashTable(Integer size) {
        this.size = size;
        this.symbols = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            this.symbols.add(new ArrayList<>());
        }
    }

    /**
     * @param symbol: String -> symbol for which the hash should be computed
     * @return: an integer representing the hash code for the specified symbol
     */
    private int hash(String symbol) {
        return symbol.codePoints().sum() % size;
    }

    /**
     * @param symbol: String -> the symbol to be added
     * @return: true or false if the symbol was successfully added to the symbols list.
     */
    public boolean addSymbol(String symbol) {
        int hashValue = hash(symbol);

        if (symbols.get(hashValue).contains(symbol)) {
            return false;
        }

        symbols.get(hashValue).add(symbol);
        return true;
    }

    /**
     * @param symbol: String -> symbol to be found
     * @return: true or false if the symbol was successfully found in the symbols list.
     */
    public boolean containsSymbol(String symbol) {
        return symbols.get(hash(symbol)).contains(symbol);
    }

    /**
     * @param symbol: String -> the symbol to be removed
     * @return: true or false if the symbol was removed from the symbols list
     */
    public boolean removeSymbol(String symbol) {
        int hashValue = hash(symbol);

        if (!symbols.get(hashValue).contains(symbol)) {
            return false;
        }

        symbols.get(hashValue).remove(symbol);
        return true;
    }

    /**
     * @param symbol: String -> symbol for which the position to be searched
     * @return: a pair the position where the symbol was found
     */
    public Pair getPosition(String symbol) {
        if (!containsSymbol(symbol))
            return null;

        return new Pair(hash(symbol), symbols.get(hash(symbol)).indexOf(symbol));
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder computedString = new StringBuilder();
        for(int i = 0; i < this.symbols.size(); i++){
            if(this.symbols.get(i).size() > 0){
                computedString.append(i);
                computedString.append(" - ");
                computedString.append(this.symbols.get(i));
                computedString.append("\n");
            }
        }
        return computedString.toString();
    }
}
