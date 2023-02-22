package model.statement;

import model.ADT.ICustomMap;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.ExprException;
import model.exceptions.StmtException;
import model.expression.Exp;
import model.type.IntType;
import model.value.Value;
import model.type.Type;

public class AssignStmt implements IStmt {
    String id;
    Exp exp;

    public AssignStmt(String id, Exp exp) {
        this.id = id;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, ExprException, StmtException {
        ICustomMap<String, Value> symTable = state.getSymTable();
        Value value = exp.eval(symTable, state.getHeap());
        if (symTable.isHere(id)) {
            Type type = (symTable.lookup(id)).getType();
            if (value.getType().equals(type)) {
                symTable.update(id, value);
            } else {
                throw new StmtException("Type of " + id + " and of the assigned value type don't match");
            }
        } else {
            throw new StmtException(id + " was not declared before");
        }
        state.setSymTable(symTable);

        return null;
    }

    @Override
    public ICustomMap<String, Type> typecheck(ICustomMap<String, Type> typeEnviroment) throws ExprException, StmtException {
        if (!typeEnviroment.isHere(id)) {
            throw new StmtException("The variable " + id + " is not defined " + this.toString());
        } else {
            Type variableType = typeEnviroment.lookup(id);
            Type expType = exp.typecheck(typeEnviroment);
            if (variableType.equals(expType)) {
                return typeEnviroment;
            } else {
                throw new StmtException("The right side and left side " + this.toString() + " has different types");
            }
        }
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(new String(id), exp.deepCopy());
    }

    @Override
    public String toString() {
        return id + "=" + exp.toString();
    }
}
