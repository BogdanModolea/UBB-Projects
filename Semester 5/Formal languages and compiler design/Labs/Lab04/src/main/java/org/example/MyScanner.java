package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;

public class MyScanner {
    private final String CONST_REGEX = "^0|[1-9]([0-9])*|[-|+][1-9]([0-9])*|'[1-9]'|'[a-zA-Z]'|\"[0-9]*[a-zA-Z ]*\"$";
    private final String IDENTIFIER_REGEX = "^[A-Za-z][A-Za-z0-9]*";

    private final ArrayList<String> operators = new ArrayList<>();
    private final ArrayList<String> separators = new ArrayList<>();
    private final ArrayList<String> reservedWords = new ArrayList<>();

    private final String filePath;
    private SymbolTable st;
    private List<Pair<String, String>> pif;

    public MyScanner(String filePath) throws FileNotFoundException {
        this.filePath = filePath;
        this.st = new SymbolTable(10);
        this.pif = new ArrayList<>();
        readTokens();
    }

    /**
     * This method reads the operator, separator, and reserved word lists from a file, populating the corresponding lists.
     *
     * @throws FileNotFoundException
     */
    public void readTokens() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("tokens/token.in"));
        while (scanner.hasNext()) {
            String read = scanner.nextLine();
            if (Objects.equals(read, "#operators")) {
                break;
            }
            operators.add(read);
        }

        while (scanner.hasNext()) {
            String read = scanner.nextLine();
            if (Objects.equals(read, "#separators")) {
                break;
            }
            if (Objects.equals(read, "space")) {
                read = " ";
            } else if (Objects.equals(read, "newline")) {
                read = "\n";
            }
            separators.add(read);
        }

        while (scanner.hasNext()) {
            String read = scanner.nextLine();
            reservedWords.add(read);
        }
    }


    /**
     * Reads the content of the source code file.
     *
     * @return the content of the source code as a String.
     * @throws FileNotFoundException
     */
    private String readProgram() throws FileNotFoundException {
        StringBuilder fileContent = new StringBuilder();
        Scanner scanner = new Scanner(new File(this.filePath));
        while (scanner.hasNext()) {
            fileContent.append(scanner.nextLine()).append("\n");
        }

        return fileContent.toString().replace("\t", "");
    }

    /**
     * Splits the source code into a list of tokens using separators.
     * Processes the list of tokens and categorizes them.
     *
     * @return a list of pairs (token, line) where each token is associated with the line it appears on.
     */
    List<Pair<String, Integer>> getTokens() {
        List<String> tokens = new ArrayList<>();
        List<Pair<String, Integer>> ans = new ArrayList<>();
        int line = 1;

        try {
            String code = readProgram();
            String separatorsString = String.join("", separators); // Concatenate separators

            StringTokenizer tokenizer = new StringTokenizer(code, separatorsString, true);

            while (tokenizer.hasMoreTokens()) {
                tokens.add(tokenizer.nextToken());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);

            if (!token.equals(" ")) {
                if (token.equals("\"")) {
                    StringBuilder string = new StringBuilder(token);
                    int j = i + 1;

                    while (!tokens.get(j).equals("\"")) {
                        string.append(tokens.get(j));
                        j++;
                    }

                    string.append(tokens.get(j));
                    i = j;
                    ans.add(new Pair<>(string.toString(), line));
                } else {
                    if (token.equals("\n")) {
                        line++;
                    } else {
                        ans.add(new Pair<>(token, line));
                    }
                }
            }
        }

        return ans;
    }

    private boolean isReservedWord(String token) {
        return reservedWords.contains(token);
    }

    private boolean isOperator(String token) {
        return operators.contains(token);
    }

    private boolean isSeparator(String token) {
        return separators.contains(token);
    }

    /**
     * Scans the source code, identifies tokens, and categorizes them as reserved words, operators, separators, constants, or identifiers.
     * Any lexical errors are reported. The results are stored in the PIF and symbol table.
     */
    public void scan() {
        List<Pair<String, Integer>> tokens = getTokens();
        for (Pair<String, Integer> tokenPair : tokens) {
            String token = tokenPair.getFirst();
            int line = tokenPair.getSecond();

            if (isReservedWord(token) || isOperator(token) || isSeparator(token)) {
                pif.add(new Pair<>(token, "-1"));
            } else if (Pattern.compile(CONST_REGEX).matcher(token).matches()) {
                st.addSymbol(token);
                pif.add(new Pair<>("CONST", st.getPosition(token).getFirst().toString()));
            } else if (Pattern.compile(IDENTIFIER_REGEX).matcher(token).matches()) {
                st.addSymbol(token);
                pif.add(new Pair<>("ID", st.getPosition(token).getFirst().toString()));
            } else {
                System.out.println("Lexical error at line " + line + ": " + token);
            }
        }
    }


    public String getPif() {
        StringBuilder computedString = new StringBuilder();
        for (Pair<String, String> pair : this.pif) {
            String first = pair.getFirst();
            String second = pair.getSecond();

            String formattedFirst = String.format("| %-10s |", first);
            String formattedSecond = (Objects.equals(second, "-1")) ? " -1" : "  " + second;

            computedString.append(formattedFirst).append(formattedSecond).append(" |\n");
        }

        return computedString.toString();
    }


    public SymbolTable getSt() {
        return this.st;
    }
}
