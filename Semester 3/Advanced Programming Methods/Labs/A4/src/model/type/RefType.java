package model.type;

import model.value.RefValue;
import model.value.Value;

import java.util.Objects;

public class RefType implements Type{

    private Type innerType;

    public RefType(Type inner) {
        this.innerType = inner;
    }

    public Type getInner() {
        return innerType;
    }


    @Override
    public Type deepCopy() {
        return new RefType(innerType.deepCopy());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RefType refType = (RefType) obj;
        return Objects.equals(innerType, refType.innerType);
    }

    @Override
    public Value defaultValue() {
        return new RefValue(innerType, 0);
    }

    @Override
    public String toString() {
        return "Ref(" + innerType + ")";
    }
}
