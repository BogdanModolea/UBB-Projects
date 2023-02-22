package model.value;

import model.type.BoolType;
import model.type.Type;

public class BoolValue implements Value {
    boolean bool;

    public BoolValue(boolean bool) {
        this.bool = bool;
    }

    public BoolValue() {
        this.bool = false;
    }

    public boolean getValue() {
        return this.bool;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(this.bool);
    }

    @Override
    public boolean equals(Object something) {
        if (this == something)
            return true;
        if (!(something instanceof BoolValue obj))
            return false;
        return obj.bool == this.bool;
    }

    @Override
    public String toString() {
        if (this.bool)
            return "true";
        return "false";
    }
}
