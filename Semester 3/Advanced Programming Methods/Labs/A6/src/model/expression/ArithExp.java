package model.expression;

import model.ADT.ICustomHeap;
import model.ADT.ICustomMap;
import model.exceptions.ExprException;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

public class ArithExp implements Exp {
    Exp exp1;
    Exp exp2;
    int op; //1-plus, 2-minus, 3-star, 4-divide

    public ArithExp(Exp e1, Exp e2, char op) {
        exp1 = e1;
        exp2 = e2;

        if (op == '+')
            this.op = 1;
        else if (op == '-')
            this.op = 2;
        else if (op == '*')
            this.op = 3;
        else if (op == '/')
            this.op = 4;
    }

    @Override
    public Value eval(ICustomMap<String, Value> tbl, ICustomHeap<Value>heap) throws ExprException {
        Value value1, value2;
        value1 = exp1.eval(tbl, heap);
        if (value1.getType().equals(new IntType())) {
            value2 = exp2.eval(tbl, heap);
            if (value2.getType().equals(new IntType())) {
                IntValue int1 = (IntValue) value1;
                IntValue int2 = (IntValue) value2;
                int x = int1.getValue();
                int y = int2.getValue();

                switch (op) {
                    case 1:
                        return new IntValue(x + y);
                    case 2:
                        return new IntValue(x - y);
                    case 3:
                        return new IntValue(x * y);
                    case 4:
                        if (y == 0)
                            throw new ExprException("Division by Zero");
                        else
                            return new IntValue(x / y);
                    default:
                        throw new ExprException("Incorrect operation");
                }
            } else {
                throw new ExprException("Second operand is not an integer");
            }
        } else {
            throw new ExprException("First operand is not an integer");
        }
    }

    @Override
    public Exp deepCopy() {
        switch (op) {
            case 1:
                return new ArithExp(exp1.deepCopy(), exp2.deepCopy(), '+');
            case 2:
                return new ArithExp(exp1.deepCopy(), exp2.deepCopy(), '-');
            case 3:
                return new ArithExp(exp1.deepCopy(), exp2.deepCopy(), '*');
            case 4:
                return new ArithExp(exp1.deepCopy(), exp2.deepCopy(), '/');
            default:
                return new ArithExp(exp1.deepCopy(), exp2.deepCopy(), '+');
        }
    }

    @Override
    public Type typecheck(ICustomMap<String, Type> typeEnviroment) throws ExprException{
        Type t1 = exp1.typecheck(typeEnviroment);
        Type t2 = exp2.typecheck(typeEnviroment);

        if(t1.equals(new IntType())){
            if(t2.equals(new IntType())){
                return new IntType();
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
    public String toString() {
        switch (op) {
            case 1:
                return exp1.toString() + "+" + exp2.toString();
            case 2:
                return exp1.toString() + "-" + exp2.toString();
            case 3:
                return exp1.toString() + "*" + exp2.toString();
            case 4:
                return exp1.toString() + '/' + exp2.toString();
            default:
                return "";
        }
    }
}
