package model.statement;

import model.ADT.ICustomMap;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.ExprException;
import model.exceptions.StmtException;
import model.expression.Exp;
import model.type.IntType;
import model.type.StringType;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt {
    private Exp exp;
    private String varName;
    private String fileName;

    public ReadFileStmt(Exp expression, String varName) {
        this.exp = expression;
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, ExprException, StmtException {
        ICustomMap<String, Value> table = state.getSymTable();
        ICustomMap<StringValue, BufferedReader> fileTable = state.getFileTable();

        if (table.isHere(varName)) {
            if (table.lookup(varName).getType().equals(new IntType())) {
                Value value = exp.eval(table ,state.getHeap());
                if (value.getType().equals(new StringType())) {
                    StringValue stringValue = (StringValue) value;
                    if (fileTable.isHere(stringValue)) {
                        BufferedReader bufferedReader = fileTable.lookup(stringValue);
                        try {
                            String line = bufferedReader.readLine();
                            Value intValue;
                            IntType type = new IntType();
                            if (line == null)
                                intValue = type.defaultValue();
                            else
                                intValue = new IntValue(Integer.parseInt(line));

                            table.update(varName, intValue);
                        } catch (IOException ex) {
                            throw new StmtException(ex.getMessage());
                        }
                    } else {
                        throw new StmtException("The file " + stringValue.getValue() + " is not in the File Table");
                    }
                } else {
                    throw new StmtException("The value couldn't be evaluated to a string value!");
                }
            } else {
                throw new StmtException(varName + " is not of type int!");
            }
        } else {
            throw new StmtException(varName + " is not defined in Sym Table");
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFileStmt(exp.deepCopy(), new String(varName));
    }

    @Override
    public String toString() {
        return "Read from " + exp + " into " + varName;
    }
}
