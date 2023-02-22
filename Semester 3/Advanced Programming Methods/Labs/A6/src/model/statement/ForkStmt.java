package model.statement;

import model.ADT.*;
import model.PrgState;
import model.exceptions.ExprException;
import model.exceptions.StmtException;
import model.type.Type;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.util.Map;

public class ForkStmt implements IStmt {
    private IStmt statement;

    public ForkStmt(IStmt statement) {
        this.statement = statement;
    }

    public PrgState execute(PrgState state) {
        ICustomStack<IStmt> stk = state.getExeStack();
        ICustomMap<String, Value> symTable = state.getSymTable();
        ICustomHeap<Value> heap = state.getHeap();
        ICustomList<Value> outList = state.getOutConsole();
        ICustomMap<StringValue, BufferedReader> fileTable = state.getFileTable();

        CustomStack<IStmt> newStk = new CustomStack<IStmt>();
        CustomMap<String, Value> newSymTable = new CustomMap<String, Value>();

        for (Map.Entry<String, Value> entry : symTable.getContent().entrySet())
            newSymTable.update(new String(entry.getKey()), entry.getValue().deepCopy());


        return new PrgState(newStk, newSymTable, outList, fileTable, heap, statement);
    }

    @Override
    public ICustomMap<String, Type> typecheck(ICustomMap<java.lang.String, model.type.Type> typeEnviroment) throws ExprException, StmtException {
        statement.typecheck(typeEnviroment.deepCopy());
        return typeEnviroment;
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(statement.deepCopy());
    }

    @Override
    public String toString() {
        return "fork(" + statement + ")";
    }
}
