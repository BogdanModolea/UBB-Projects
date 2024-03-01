package org.example;

import java.io.Serializable;

public class DTO implements Serializable {
    public Polynomial a;
    public Polynomial b;
    public int begin;
    public int end;

    public DTO(Polynomial a, Polynomial b, int begin, int end) {
        this.a = a;
        this.b = b;
        this.begin = begin;
        this.end = end;
    }
}