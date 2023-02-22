package model.value;

import model.type.IntType;
import model.type.Type;

public class IntValue implements Value {
    int x;

    public IntValue(int x) {
        this.x = x;
    }

    public IntValue() {
        this.x = 0;
    }

    public int getValue() {
        return this.x;
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public Value deepCopy() {
        return new IntValue(this.x);
    }

    @Override
    public boolean equals(Object something) {
        if (this == something) return true;
        if (!(something instanceof IntValue obj))
            return false;
        return obj.x == this.x;
    }

    @Override
    public String toString() {
        return Integer.toString(this.x);
    }
}
