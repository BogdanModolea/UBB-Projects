package model.statement;

import model.ADT.ICustomMap;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.ExprException;
import model.exceptions.StmtException;
import model.expression.Exp;
import model.type.StringType;
import model.type.Type;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStmt implements IStmt {
    private Exp exp;

    public CloseRFileStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, ExprException, StmtException {
        ICustomMap<String, Value> symTable = state.getSymTable();
        Value value = exp.eval(symTable, state.getHeap());

        if (value.getType().equals(new StringType())) {
            ICustomMap<StringValue, BufferedReader> fileTable = state.getFileTable();
            StringValue stringValue = (StringValue) value;

            if (fileTable.isHere(stringValue)) {
                BufferedReader bufferedReader = fileTable.lookup(stringValue);
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw new StmtException(ex.getMessage());
                }
                fileTable.remove(stringValue);
            } else {
                throw new StmtException("The file doesn't exist in the File Table");
            }
        } else {
            throw new StmtException("Expression could not be evaluated");
        }

        return null;
    }

    @Override
    public ICustomMap<String, Type> typecheck(ICustomMap<java.lang.String, model.type.Type> typeEnviroment) throws ExprException, StmtException {
        Type expType = exp.typecheck(typeEnviroment);
        if (expType.equals(new StringType())) {
            return typeEnviroment;
        } else {
            throw new StmtException("The close file expression " + this.toString() + " is not a string");
        }
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return "close(" + exp + ")";
    }
}
