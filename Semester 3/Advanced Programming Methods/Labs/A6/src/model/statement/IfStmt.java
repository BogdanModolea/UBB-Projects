package model.statement;

import model.ADT.ICustomMap;
import model.ADT.ICustomStack;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.ExprException;
import model.exceptions.StmtException;
import model.expression.Exp;
import model.type.BoolType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public class IfStmt implements IStmt {
    private Exp expression;
    private IStmt thenStatement;
    private IStmt elseStatement;

    public IfStmt(Exp expression, IStmt thenStatement, IStmt elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, ExprException, StmtException {
        ICustomStack<IStmt> stack = state.getExeStack();
        Value condition = expression.eval(state.getSymTable(), state.getHeap());

        if (!condition.getType().equals(new BoolType()))
            throw new StmtException("Condition must be a boolean");

        if (condition.equals(new BoolValue(true)))
            stack.push(thenStatement.deepCopy());
        else
            stack.push(elseStatement.deepCopy());

        state.setExeStack(stack);
        return null;
    }

    @Override
    public ICustomMap<String, Type> typecheck(ICustomMap<java.lang.String, model.type.Type> typeEnviroment) throws ExprException, StmtException {
        Type expType = expression.typecheck(typeEnviroment);
        if (expType.equals(new BoolType())) {
            thenStatement.typecheck(typeEnviroment.deepCopy());
            elseStatement.typecheck(typeEnviroment.deepCopy());
            return typeEnviroment;
        } else {
            throw new StmtException("The condition in " + this.toString() + " statement is not a boolean");
        }
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(expression.deepCopy(), thenStatement.deepCopy(), elseStatement.deepCopy());
    }

    @Override
    public String toString() {
        return "if (" + expression + ") then {" + thenStatement + "} else {" + elseStatement + "}";
    }
}
