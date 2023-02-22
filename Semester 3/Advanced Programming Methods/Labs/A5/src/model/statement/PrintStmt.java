package model.statement;

import model.ADT.ICustomList;
import model.ADT.ICustomStack;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.ExprException;
import model.exceptions.StmtException;
import model.expression.Exp;
import model.value.Value;

public class PrintStmt implements IStmt {
    Exp exp;

    public PrintStmt(Exp expression) {
        exp = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, ExprException, StmtException {
        ICustomStack<IStmt> stack = state.getExeStack();
        ICustomList<Value> outConsole = state.getOutConsole();
        outConsole.add(exp.eval(state.getSymTable(), state.getHeap()));
        state.setExeStack(stack);
        state.setOutConsole(outConsole);

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "print(" + exp.toString() + ")";
    }
}
