
import org.example.SymbolTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

public class SymTableTest {
    SymbolTable table;

    @BeforeEach
    public void setup() {
        table = new SymbolTable(8);
    }

    @Test
    public void testSize() {
        assertThat(table.getSize(), is(8));
    }

    @Test
    public void testAdd() {
        assertThat(table.addSymbol("x"), is(true));
        assertThat(table.addSymbol("y"), is(true));
        assertThat(table.addSymbol("x"), is(false));
    }

    @Test
    void testRemove() {
        table.addSymbol("x");
        table.addSymbol("y");
        assertThat(table.removeSymbol("x"), is(true));
        assertThat(table.removeSymbol("y"), is(true));
        assertThat(table.removeSymbol("z"), is(false));
    }

    @Test void testContains() {
        table.addSymbol("x");
        table.addSymbol("y");
        assertThat(table.containsSymbol("x"), is(true));
        assertThat(table.containsSymbol("y"), is(true));
        assertThat(table.containsSymbol("z"), is(false));
    }
}
