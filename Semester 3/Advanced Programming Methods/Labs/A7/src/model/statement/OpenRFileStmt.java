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
import java.io.FileReader;
import java.io.Reader;
import java.io.FileNotFoundException;

public class OpenRFileStmt implements IStmt {
    private Exp exp;

    public OpenRFileStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, ExprException, StmtException {
        ICustomMap<String, Value> table = state.getSymTable();
        Value value = exp.eval(table, state.getHeap());

        if (value.getType().equals(new StringType())) {
            ICustomMap<StringValue, BufferedReader> fileTable = state.getFileTable();
            StringValue stringValue = (StringValue) value;

            if (!fileTable.isHere(stringValue)) {
                try {
                    Reader reader = new FileReader(stringValue.getValue());
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    fileTable.update(stringValue, bufferedReader);
                } catch (FileNotFoundException ex) {
                    throw new StmtException(ex.getMessage());
                }
            } else {
                throw new StmtException("The file is already in use");
            }
        } else {
            throw new StmtException("Expression couldn't be evaluated");
        }

        return null;
    }

    @Override
    public ICustomMap<String, Type> typecheck(ICustomMap<java.lang.String, model.type.Type> typeEnviroment) throws ExprException, StmtException{
        Type expType = exp.typecheck(typeEnviroment);
        if(expType.equals(new StringType())){
            return typeEnviroment;
        }
        else{
            throw new StmtException("The open file expression " + this.toString() + " is not a string");
        }
    }


    @Override
    public IStmt deepCopy() {
        return new OpenRFileStmt(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "Open(" + exp + ")";
    }
}
