import com.example.BigDec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestBigDec {
    private BigDec operation;

    @BeforeEach
    public void setup() {
        List<BigDecimal> list = Arrays.asList(
                new BigDecimal("4500000.0"),
                new BigDecimal("1000000.0"),
                new BigDecimal("1234567.0")
        );

        operation = new BigDec(list);
    }

    @Test
    void testAdd() {
        assertThat(operation.bigDecSum(), is(new BigDecimal("6734567.0")));
    }

    @Test
    void testAvg() {
        assertThat(operation.bigDecAvg(), is(new BigDecimal("2244855.7")));
    }

    @Test
    void test10Percent() {
        assertThat(operation.bigDecTop10Percent().size(), is(1));
        List<BigDecimal> result = new ArrayList<>();
        result.add(new BigDecimal("4500000.0"));
        assertThat(operation.bigDecTop10Percent(), is(result));
    }
}
