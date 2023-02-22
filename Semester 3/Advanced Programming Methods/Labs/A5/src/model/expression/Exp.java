package model.expression;

import model.ADT.ICustomHeap;
import model.exceptions.ExprException;
import model.ADT.ICustomMap;
import model.value.Value;

public interface Exp {
    public Value eval(ICustomMap<String, Value> tbl, ICustomHeap<Value>heap) throws ExprException;

    Exp deepCopy();
}
