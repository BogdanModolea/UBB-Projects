package model;

import model.ADT.*;
import model.exceptions.ADTException;
import model.exceptions.CustomException;
import model.exceptions.ExprException;
import model.exceptions.StmtException;
import model.statement.IStmt;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.util.Objects;

public class PrgState {
    private ICustomStack<IStmt> exeStack;
    private ICustomHeap<Value> heap;
    private ICustomMap<String, Value> symTable;
    private ICustomList<Value> out;
    private ICustomMap<StringValue, BufferedReader> fileTable;
    private IStmt originalProgram;
    private static int stateID;
    private static int freeID = 0;

    public PrgState(ICustomStack<IStmt> stack, ICustomMap<String, Value> symTable, ICustomList<Value> out, ICustomMap<StringValue, BufferedReader> fileTable, ICustomHeap<Value> heap, IStmt program) {
        this.exeStack = stack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.originalProgram = program;
        this.heap = heap;
        stack.push(program);
        stateID = getNewPrgStateID();
    }

    public PrgState(ICustomStack<IStmt> stack, ICustomMap<String, Value> symTable, ICustomList<Value> out) {
        this.exeStack = stack;
        this.symTable = symTable;
        this.out = out;
        this.heap = new CustomHeap<Value>();
        stateID = getNewPrgStateID();
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

    public ICustomHeap<Value> getHeap() {
        return heap;
    }

    public void setHeap(ICustomHeap<Value> heap) {
        this.heap = heap;
    }

    public int getStateID() {
        return stateID;
    }

    public static synchronized int getNewPrgStateID() {
        freeID++;
        return freeID;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("Program state\n");
        string.append("ID: ").append(stateID).append(" \n");
        string.append("Exe Stack: ").append(this.exeStack).append(" \n");
        string.append("Sym Table: ").append(this.symTable).append(" \n");
        string.append("Heap: ").append(this.heap).append("\n");
        string.append("Output Console: ").append(this.out).append(" \n");
        string.append("File Table: ").append(this.fileTable).append(" \n");

        return string.toString();
    }

    public PrgState oneStep() throws ADTException, ExprException, StmtException, CustomException {
        if (exeStack.isEmpty()) {
            throw new CustomException("Program stack is empty");
        }
        IStmt currentStatement = exeStack.pop();
        return currentStatement.execute(this);
    }

    public boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    public boolean equals(PrgState prg) {
        return prg.getHeap() == this.getHeap() && prg.getSymTable() == this.getSymTable() && prg.getExeStack() == this.getExeStack() && prg.getStateID() == this.getStateID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getStateID());
    }
}
