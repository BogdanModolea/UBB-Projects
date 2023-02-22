package model.expression;

import model.ADT.ICustomHeap;
import model.ADT.ICustomMap;
import model.exceptions.ExprException;
import model.type.IntType;
import model.type.Type;
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
    public Type typecheck(ICustomMap<String, Type> typeEnviroment) throws ExprException{
        if(typeEnviroment.isHere(id)){
            return typeEnviroment.lookup(id);
        }
        else{
            throw new ExprException("The variable " + this.toString() + " is not defined.");
        }
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
