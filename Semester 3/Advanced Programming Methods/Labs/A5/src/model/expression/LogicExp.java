package model.expression;

import model.ADT.ICustomHeap;
import model.ADT.ICustomMap;
import model.exceptions.ExprException;
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
    public Exp deepCopy() {
        return new LogicExp(exp1.deepCopy(), exp2.deepCopy(), op);
    }
}
