package com.example;

import org.example.Min;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestMin {
    private Min min;

    @BeforeEach
    public void setup() {
        min = new Min();
    }

    @Test
    public void minBetweenTwoNumbers() {
        assertThat(min.execute(2, 3), is(2));
    }

    @Test
    public void minBetweenTwoNumbersOneNegative() {
        assertThat(min.execute(2, -3), is(-3));
    }

}
