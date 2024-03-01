import com.example.BigDec;
import com.example.DoubleObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestDoubleObj {
    private DoubleObject operation;

    @BeforeEach
    public void setup() {
        List<Double> list = Arrays.asList(
                new Double(45000d),
                new Double(10000d),
                new Double(12345d)
        );
        operation = new DoubleObject(list);
    }

    @Test
    void testAdd() {
        assertThat(operation.doubleObjSum(), is(new Double(67345d)));
    }

    @Test
    void testAvg() {
        assertThat(operation.doubleObjAvg(), is(new Double("22448.333333333332")));
    }

    @Test
    void test10Percent() {
        assertThat(operation.doubleObjTop10Percent().size(), is(1));
        List<Double> result = new ArrayList<>();
        result.add(new Double(45000d));
        assertThat(operation.doubleObjTop10Percent(), is(result));
    }

}
