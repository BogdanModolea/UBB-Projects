package model.expression;

import model.ADT.ICustomHeap;
import model.ADT.ICustomMap;
import model.exceptions.ExprException;
import model.type.BoolType;
import model.type.IntType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public class LogicExp implements Exp {
    Exp exp1;
    Exp exp2;
    int op;

    public LogicExp(Exp exp1, Exp exp2, int op) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.op = op;
    }

    @Override
    public Value eval(ICustomMap<String, Value> tbl, ICustomHeap<Value> heap) throws ExprException {
        Value value1, value2;
        value1 = exp1.eval(tbl, heap);
        if (value1.getType().equals(new BoolValue())) {
            value2 = exp2.eval(tbl, heap);
            if (value2.getType().equals(new BoolValue())) {
                BoolValue bool1 = (BoolValue) value1;
                BoolValue bool2 = (BoolValue) value2;
                boolean x = bool1.getValue();
                boolean y = bool2.getValue();

                if (op == 1)
                    return new BoolValue(x && y);
                else if (op == 2)
                    return new BoolValue(x || y);

            } else {
                throw new ExprException("Second operand is not a boolean");
            }
        } else {
            throw new ExprException("First operand is not a boolean");
        }

        return new BoolValue(false);
    }

    @Override
    public Type typecheck(ICustomMap<String, Type> typeEnviroment) throws ExprException {
        Type t1 = exp1.typecheck(typeEnviroment);
        Type t2 = exp2.typecheck(typeEnviroment);

        if (t1.equals(new BoolType())) {
            if (t2.equals(new BoolType())) {
                return new BoolType();
            } else {
                throw new ExprException("Second operand is not an boolean " + this.toString());
            }
        } else {
            throw new ExprException("First operand is not an boolean " + this.toString());
        }
    }

    @Override
    public Exp deepCopy() {
        return new LogicExp(exp1.deepCopy(), exp2.deepCopy(), op);
    }
}
