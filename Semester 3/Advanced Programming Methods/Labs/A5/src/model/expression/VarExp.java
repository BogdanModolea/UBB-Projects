package model.expression;

import model.ADT.ICustomHeap;
import model.ADT.ICustomMap;
import model.exceptions.ExprException;
import model.value.Value;

public class VarExp implements Exp {
    String id;

    public VarExp(String id) {
        this.id = id;
    }

    @Override
    public Value eval(ICustomMap<String, Value> tbl, ICustomHeap<Value> heap) throws ExprException {
        return tbl.lookup(id);
    }

    @Override
    public Exp deepCopy() {
        return new VarExp(new String(id));
    }

    @Override
    public String toString() {
        return id;
    }
}
