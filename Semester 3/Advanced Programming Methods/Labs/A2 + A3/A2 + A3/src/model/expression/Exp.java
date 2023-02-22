package model.expression;

import model.exceptions.ExprException;
import model.ADT.ICustomMap;
import model.value.Value;

public interface Exp {
    public Value eval(ICustomMap<String, Value> tbl) throws ExprException;

    Exp deepCopy();
}
