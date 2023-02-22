package model.expression;

import model.ADT.ICustomHeap;
import model.ADT.ICustomMap;
import model.exceptions.ExprException;
import model.value.Value;

public class ValueExp implements Exp{
    Value value;

    public ValueExp(Value value) {
        this.value = value;
    }

    @Override
    public Value eval(ICustomMap<String, Value> tbl, ICustomHeap<Value> heap) throws ExprException {
        return value;
    }

    @Override
    public Exp deepCopy() {
        return new ValueExp(value.deepCopy());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
