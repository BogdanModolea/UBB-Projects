package model.expression;

import model.ADT.ICustomHeap;
import model.ADT.ICustomMap;
import model.exceptions.ExprException;
import model.value.RefValue;
import model.value.Value;

public class ReadHeapExp implements Exp{

    private Exp exp;

    public ReadHeapExp(Exp exp) {
        this.exp = exp;
    }


    @Override
    public Value eval(ICustomMap<String, Value> tbl, ICustomHeap<Value> heap) throws ExprException {
        Value val = exp.eval(tbl, heap);
        if (val instanceof RefValue) {
            RefValue refVal = (RefValue) val;
            if (heap.contains(refVal.getAddress())) {
                return heap.get(refVal.getAddress());
            } else {
                throw new ExprException("No address in the heap");
            }

        } else {
            throw new ExprException("The expression could not be evaluated to a RefValue");
        }
    }

    @Override
    public Exp deepCopy() {
        return new ReadHeapExp(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "rH(" + exp + ")";
    }
}
