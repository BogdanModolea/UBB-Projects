package model.statement;
import model.ADT.ICustomMap;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.ExprException;
import model.exceptions.StmtException;
import model.type.Type;

public interface IStmt {
    public PrgState execute(PrgState state) throws ADTException, ExprException, StmtException;

    public ICustomMap<String, Type> typecheck(ICustomMap<java.lang.String, model.type.Type> typeEnviroment) throws ExprException, StmtException;

    IStmt deepCopy();
}
