package model.statement;

import model.ADT.ICustomMap;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.ExprException;
import model.exceptions.StmtException;
import model.expression.Exp;
import model.type.StringType;
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
        Value value = exp.eval(symTable);

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
    public IStmt deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return "close(" + exp + ")";
    }
}
