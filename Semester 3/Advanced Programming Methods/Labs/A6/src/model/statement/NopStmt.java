package model.statement;

import model.ADT.ICustomMap;
import model.PrgState;
import model.exceptions.ExprException;
import model.exceptions.StmtException;
import model.type.Type;

public class NopStmt implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws StmtException {
        return null;
    }

    @Override
    public ICustomMap<String, Type> typecheck(ICustomMap<java.lang.String, model.type.Type> typeEnviroment) throws ExprException, StmtException{
        return typeEnviroment;
    }

    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }
}
