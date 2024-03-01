package com.example;

import org.example.Max;
import org.example.Min;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestMax {
    private Max max;

    @BeforeEach
    public void setup() {
        max = new Max();
    }

    @Test
    public void maxBetweenTwoNumbers() {
        assertThat(max.execute(2, 3), is(3));
    }

    @Test
    public void maxBetweenTwoNumbersOneNegative() {
        assertThat(max.execute(2, -3), is(2));
    }

}
