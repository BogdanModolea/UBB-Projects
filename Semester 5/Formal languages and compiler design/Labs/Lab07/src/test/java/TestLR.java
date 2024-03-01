import org.example.Grammar;
import org.example.Item;
import org.example.Parser;
import org.example.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
public class TestLR {
    private Grammar grammar;
    private Parser parser;

    @BeforeEach
    public void setup() {
        grammar = new Grammar("D:/.LFTC/LFTC/Lab07/grammar/g2.txt");
        parser = new Parser(grammar);
    }

    @Test
    void testClosure() {
        parser.canonicalCollection();
        Item item = new Item(
                parser.getEnhancedGrammar().getStartSymbol(),
                parser.getEnhancedGrammar().getProductionForNonTerminal(parser.getGrammar().getStartSymbol()).get(0),
                0);
        Object[] result = parser.closure(item).getItemSet().toArray();
        System.out.println("The result is here:");

        assert result.length == 1;
        Item testFinal = new Item("S0", Arrays.asList("2", "A"), 0);
        assert result[0].equals(testFinal);
    }

    @Test
    void testGoTo() {
        parser.canonicalCollection();

        Item item = new Item(
                parser.getEnhancedGrammar().getStartSymbol(),
                parser.getEnhancedGrammar().getProductionForNonTerminal(parser.getGrammar().getStartSymbol()).get(0),
                0
        );
        State state = parser.closure(item);

        State result = parser.goTo(state, state.getSymbolsSucceedingTheDot().get(0));

        assert result.getItemSet().size() == 3;
        assert result.getItemSet().toArray()[0].equals(new Item("A", Arrays.asList("0"), 0));
        assert result.getItemSet().toArray()[1].equals(new Item("S0", Arrays.asList("2", "A"), 1));
        assert result.getItemSet().toArray()[2].equals(new Item("A", Arrays.asList("2"), 0));
    }

    @Test
    public void testCanonicalCollection() {
        List<State> result = parser.canonicalCollection().getStates();
        assert result.size() == 10;
        assert result.get(0).getItemSet().toArray()[0].equals(new Item("S", Arrays.asList("2", "A"), 0));
    }
}
