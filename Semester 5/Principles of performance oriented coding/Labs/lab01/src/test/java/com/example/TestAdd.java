package com.example;

import org.example.Add;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

public class TestAdd {
    private Add add;

    @BeforeEach
    public void setup() {
        add = new Add();
    }

    @Test
    public void addingTwoNumbers() {
        assertThat(add.execute(2, 3), is(5));
    }

    @Test
    public void addingTwoNumbersOneNegative() {
        assertThat(add.execute(2, -3), is(-1));
    }
}
