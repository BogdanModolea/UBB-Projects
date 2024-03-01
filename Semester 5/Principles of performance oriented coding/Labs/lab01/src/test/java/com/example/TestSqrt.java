package com.example;

import org.example.Add;
import org.example.Sqrt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestSqrt {
    private Sqrt sqrt;
    @BeforeEach
    public void setup() {
        sqrt = new Sqrt();
    }

    @Test
    public void sqrtWithIntRoot() {
        assertThat(sqrt.execute(4), is(2.0));
    }

    @Test
    public void sqrtWithNoIntRoot() {
        assertThat(sqrt.execute(2), is(1.4142135623730951));
    }

}
