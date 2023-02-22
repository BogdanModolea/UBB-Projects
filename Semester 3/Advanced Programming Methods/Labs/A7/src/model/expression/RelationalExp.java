package model.expression;

import model.ADT.ICustomHeap;
import model.ADT.ICustomMap;
import model.exceptions.ExprException;
import model.type.BoolType;
import model.type.IntType;
import model.type.Type;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;

public class RelationalExp implements Exp {
    private Exp exp1;
    private Exp exp2;
    private int op;

    public RelationalExp(Exp exp1, Exp exp2, int op) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.op = op;
    }

    @Override
    public Value eval(ICustomMap<String, Value> tbl, ICustomHeap<Value> heap) throws ExprException {
        Value value1, value2;
        value1 = exp1.eval(tbl, heap);
        value2 = exp2.eval(tbl, heap);
        if (value1.getType().equals(new IntType()) && value2.getType().equals(new IntType())) {
            IntValue intValue1, intValue2;
            intValue1 = (IntValue) value1;
            intValue2 = (IntValue) value2;
            int x = intValue1.getValue();
            int y = intValue2.getValue();

            switch (op) {
                case 1:
                    return new BoolValue(x < y);
                case 2:
                    return new BoolValue(x <= y);
                case 3:
                    return new BoolValue(x == y);
                case 4:
                    return new BoolValue(x != y);
                case 5:
                    return new BoolValue(x > y);
                case 6:
                    return new BoolValue(x >= y);
            }
        } else {
            throw new ExprException("At least one operand is not an integer");
        }

        return new BoolValue(false);
    }

    @Override
    public Type typecheck(ICustomMap<String, Type> typeEnviroment) throws ExprException{
        Type t1 = exp1.typecheck(typeEnviroment);
        Type t2 = exp2.typecheck(typeEnviroment);

        if(t1.equals(new IntType())){
            if(t2.equals(new IntType())){
                return new BoolType();
            }
            else{
                throw new ExprException("Second operand is not an integer " + this.toString());
            }
        }
        else {
            throw new ExprException("First operand is not an integer "  + this.toString());
        }
    }

    @Override
    public Exp deepCopy() {
        return new RelationalExp(exp1.deepCopy(), exp2.deepCopy(), op);
    }

    @Override
    public String toString() {
        String s = "";
        switch (op) {
            case 1:
                s = "<";
                break;
            case 2:
                s = "<=";
                break;
            case 3:
                s = "==";
                break;
            case 4:
                s = "!=";
                break;
            case 5:
                s = ">";
                break;
            default:
                s = ">=";
        }
        return exp1 + s + exp2;
    }
}
