package model.expression;

import model.ADT.ICustomHeap;
import model.exceptions.ExprException;
import model.ADT.ICustomMap;
import model.type.Type;
import model.value.Value;

public interface Exp {
    public Value eval(ICustomMap<String, Value> tbl, ICustomHeap<Value>heap) throws ExprException;

    Type typecheck(ICustomMap<String, Type> typeEnv) throws ExprException;

    Exp deepCopy();
}
