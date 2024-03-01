package com.example;

import org.example.Add;
import org.example.Multiply;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestMultiply {
    private Multiply multiply;

    @BeforeEach
    public void setup() {
        multiply = new Multiply();
    }

    @Test
    public void multiplyingTwoNumbers() {
        assertThat(multiply.execute(2, 3), is(6));
    }

    @Test
    public void multiplyingTwoNumbersOneNegative() {
        assertThat(multiply.execute(2, -3), is(-6));
    }

}
