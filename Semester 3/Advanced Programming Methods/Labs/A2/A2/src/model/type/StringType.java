package model.type;

import model.value.StringValue;
import model.value.Value;

public class StringType implements Type {
    @Override
    public Type deepCopy() {
        return new StringType();
    }

    @Override
    public Value defaultValue() {
        return new StringValue("");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        return obj != null && getClass() == obj.getClass();
    }

    @Override
    public String toString() {
        return "string";
    }
}
