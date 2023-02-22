package model.statement;

import model.ADT.ICustomHeap;
import model.ADT.ICustomMap;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.ExprException;
import model.exceptions.StmtException;
import model.expression.Exp;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

public class WriteHeapStmt implements IStmt{

    private String varName;
    private Exp exp;

    public WriteHeapStmt(String varName, Exp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, ExprException, StmtException {
        ICustomMap<String, Value> symTable = state.getSymTable();
        ICustomHeap<Value> heap = state.getHeap();

        if (symTable.isHere(varName)) {
            if (symTable.lookup(varName).getType() instanceof RefType) {
                RefValue refVal = (RefValue) symTable.lookup(varName);
                if (heap.contains(refVal.getAddress())) {
                    Value val = exp.eval(symTable, heap);
                    if (symTable.lookup(varName).getType().equals(new RefType(val.getType()))) {
                        int address = refVal.getAddress();
                        heap.update(address, val);
                    }
                    else {
                        throw new StmtException("The pointing variable has a different type than the evaluated expression.");
                    }
                }
                else {
                    throw new StmtException("The address to which " + varName + " points is not in the heap");
                }
            }
            else {
                throw new StmtException(varName + " is not a reference variable");
            }
        }
        else {
            throw new StmtException(varName + " is not defined");
        }

        return null;
    }

    @Override
    public ICustomMap<String, Type> typecheck(ICustomMap<java.lang.String, model.type.Type> typeEnviroment) throws ExprException, StmtException {
        if (typeEnviroment.isHere(varName)) {
            Type varType = typeEnviroment.lookup(varName);
            Type expType = exp.typecheck(typeEnviroment);
            if (!(varType instanceof RefType)) {
                throw new StmtException("The file name is " + this.toString() + " is not a string");
            }
            if (!varType.equals(new RefType(expType))) {
                throw new StmtException("The right side of " + this.toString() + " has other type than the referenced type of the left side");
            }
            return typeEnviroment;
        } else {
            throw new StmtException("The variable " + varName + " is not defined");
        }
    }

    @Override
    public IStmt deepCopy() {
        return new WriteHeapStmt(new String(varName), exp.deepCopy());
    }

    @Override
    public String toString() {
        return "wH(" + varName + "," + exp + ")";
    }
}
