package model.statement;

import model.PrgState;
import model.exceptions.StmtException;

public class NopStmt implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws StmtException {
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }
}
