package model.statement;

import model.ADT.ICustomHeap;
import model.ADT.ICustomMap;
import model.ADT.ICustomStack;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.ExprException;
import model.exceptions.StmtException;
import model.expression.Exp;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

public class NewHeapStmt implements IStmt{

    String varName;
    Exp exp;

    public NewHeapStmt(String varName, Exp exp) {
        this.varName = varName;
        this.exp = exp;
    }


    @Override
    public PrgState execute(PrgState state) throws ADTException, ExprException, StmtException {
        ICustomStack<IStmt>stack = state.getExeStack();
        ICustomMap<String, Value> symTable = state.getSymTable();
        ICustomHeap<Value> heap = state.getHeap();

        if(symTable.isHere(varName)){
            if(symTable.lookup(varName).getType() instanceof RefType){
                Value val = exp.eval(symTable, heap);
                Value tblValue = symTable.lookup(varName);
                if(val.getType().equals(((RefType)(tblValue.getType())).getInner())){
                    int address = heap.allocate(val);
                    symTable.update(varName, new RefValue(val.getType(), address));
                }
                else{
                    throw new StmtException("Value's type is not correct");
                }
            }
            else{
                throw new StmtException("Value's type is not reference!");
            }
        }
        else{
            throw new StmtException("Value is not declared!");
        }

        state.setSymTable(symTable);
        state.setHeap(heap);
        state.setExeStack(stack);

        return null;
    }

    @Override
    public ICustomMap<String, Type> typecheck(ICustomMap<java.lang.String, model.type.Type> typeEnviroment) throws ExprException, StmtException {
        if (!typeEnviroment.isHere(varName)) {
            throw new StmtException("The variable " + varName + " not defined");
        } else {
            Type varType = typeEnviroment.lookup(varName);
            Type expType = exp.typecheck(typeEnviroment);
            if (varType.equals(new RefType(expType))) {
                return typeEnviroment;
            } else {
                throw new StmtException("The right side and left side in " + this.toString() + " have different types");
            }
        }
    }

    @Override
    public IStmt deepCopy() {
        return new NewHeapStmt(varName, exp.deepCopy());
    }

    @Override
    public String toString(){
        return "new(" + varName + ", " + exp + ")";
    }
}
