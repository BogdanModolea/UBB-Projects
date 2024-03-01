package com.example;

import org.example.Subtract;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestSubtract {
    private Subtract subtract;

    @BeforeEach
    public void setup() {
        subtract = new Subtract();
    }

    @Test
    public void subtractingTwoNumbers() {
        assertThat(subtract.execute(3, 2), is(1));
    }

    @Test
    public void subtractingTwoNumbersOneNegative() {
        assertThat(subtract.execute(2, -3), is(5));
    }
}
