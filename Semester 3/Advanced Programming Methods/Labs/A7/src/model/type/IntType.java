package model.type;

import model.value.IntValue;
import model.value.Value;

public class IntType implements Type {
    @Override
    public Type deepCopy() {
        return new IntType();
    }

    @Override
    public Value defaultValue() {
        return new IntValue(0);
    }

    @Override
    public boolean equals(Object something) {
        return something instanceof IntType;
    }

    @Override
    public String toString() {
        return "int";
    }
}
