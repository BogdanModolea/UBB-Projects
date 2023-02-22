package model.type;

import model.value.BoolValue;
import model.value.Value;

public class BoolType implements Type{

    @Override
    public Type deepCopy() {
        return new BoolType();
    }

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public boolean equals(Object something) {
        return something instanceof BoolType;
    }

    @Override
    public String toString() {
        return "bool";
    }
}
