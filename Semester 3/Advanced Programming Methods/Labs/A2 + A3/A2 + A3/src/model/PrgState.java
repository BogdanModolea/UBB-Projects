package model;

import model.ADT.*;
import model.statement.IStmt;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;

public class PrgState {
    private ICustomStack<IStmt> exeStack;
    private ICustomMap<String, Value> symTable;
    private ICustomList<Value> out;

    private ICustomMap<StringValue, BufferedReader> fileTable;
    private IStmt originalProgram;

    public PrgState(ICustomStack<IStmt> stack, ICustomMap<String, Value> symTable, ICustomList<Value> out, ICustomMap<StringValue, BufferedReader> fileTable, IStmt program) {
        this.exeStack = stack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.originalProgram = program;
        stack.push(program);
    }

    public PrgState(ICustomStack<IStmt> stack, ICustomMap<String, Value> symTable, ICustomList<Value> out) {
        this.exeStack = stack;
        this.symTable = symTable;
        this.out = out;
    }

    public ICustomStack<IStmt> getExeStack() {
        return exeStack;
    }

    public void setExeStack(ICustomStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public ICustomMap<String, Value> getSymTable() {
        return symTable;
    }

    public void setSymTable(ICustomMap<String, Value> symTable) {
        this.symTable = symTable;
    }

    public ICustomMap<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setFileTable(ICustomMap<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public void setOriginalProgram(IStmt originalProgram) {
        this.originalProgram = originalProgram;
    }

    public ICustomList<Value> getOutConsole() {
        return out;
    }

    public void setOutConsole(ICustomList<Value> outConsole) {
        out = outConsole;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("Program state\n");
        string.append("Exe Stack: ").append(this.exeStack).append(" \n");
        string.append("Sym Table: ").append(this.symTable).append(" \n");
        string.append("Output Console: ").append(this.out).append(" \n");
        string.append("File Table: ").append(this.fileTable).append(" \n");

        return string.toString();
    }
}
