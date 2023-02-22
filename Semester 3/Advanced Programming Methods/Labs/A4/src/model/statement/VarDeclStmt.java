package model.statement;

import model.ADT.ICustomMap;
import model.ADT.ICustomStack;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.ExprException;
import model.exceptions.StmtException;
import model.type.BoolType;
import model.type.IntType;
import model.type.StringType;
import model.type.Type;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;


public class VarDeclStmt implements IStmt {
    String name;
    Type type;

    public VarDeclStmt(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, ExprException, StmtException {
        ICustomStack<IStmt> stack = state.getExeStack();
        ICustomMap<String, Value> table = state.getSymTable();
        if (table.isHere(name))
            throw new StmtException("Variable is already declared");
        else {
//            if (type.equals(new IntType()))
//                table.add(name, new IntValue());
//            else if (type.equals(new BoolType()))
//                table.add(name, new BoolValue());
//            else if (type.equals(new StringType()))
//                table.add(name, new StringValue());
//            else
//                throw new StmtException("Type does not exist");
            table.add(name, type.defaultValue());
        }
        state.setSymTable(table);
        state.setExeStack(stack);

        return state;
    }

        @Override
        public IStmt deepCopy () {
            return new VarDeclStmt(new String(name), type.deepCopy());
        }

        @Override
        public String toString () {
            return type + " " + name;
        }
    }
