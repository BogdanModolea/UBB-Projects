package com.example;

import org.example.Add;
import org.example.Division;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestDivision {
    private Division division;

    @BeforeEach
    public void setup() {
        division = new Division();
    }

    @Test
    public void dividingTwoNumbers() {
        assertThat(division.execute(3,2), is(1.5f));
    }

    @Test
    public void dividingTwoNumbersOneNegative() {
        assertThat(division.execute(2, -3), is(-0.6666667f));
    }

    @Test
    public void dividingByZero() {
        assertThat(Double.isInfinite(division.execute(2, 0)), is(true));
    }
}
