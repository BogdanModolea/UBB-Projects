import com.example.DoubleObject;
import com.example.DoublePrimitive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestDoublePrimitive {
    private DoublePrimitive operation;

    @BeforeEach
    public void setup() {
        double[] list = {45000, 10000, 12345};
        operation = new DoublePrimitive(list);
    }

    @Test
    void testAdd() {
        assertThat(operation.doublePrimSum(), is(67345.0));
    }

    @Test
    void testAvg() {
        assertThat(operation.doublePrimAvg(), is(22448.333333333332));
    }

    @Test
    void test10Percent() {
        assertThat(operation.doublePrimTop10Percent().length, is(1));
        double[] result = {45000};
        assertThat(operation.doublePrimTop10Percent(), is(result));
    }
}
