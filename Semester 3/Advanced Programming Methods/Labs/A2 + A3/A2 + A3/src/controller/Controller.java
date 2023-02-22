package controller;

import model.ADT.*;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.CustomException;
import model.exceptions.ExprException;
import model.exceptions.StmtException;
import model.expression.ValueExp;
import model.expression.VarExp;
import model.statement.*;
import model.type.IntType;
import model.value.IntValue;
import model.value.Value;
import repository.IRepo;

import java.io.IOException;

public class Controller {
    private IRepo repository;

    public Controller(IRepo repo){
        this.repository = repo;
    }

    public PrgState oneStep(PrgState state) throws CustomException, ADTException, StmtException, ExprException{
        ICustomStack<IStmt> stack = state.getExeStack();
        if(stack.isEmpty())
            throw new CustomException("Stack is empty");

        IStmt currentStmt = stack.pop();
        return currentStmt.execute(state);
    }

    public void allStep() throws CustomException, IOException{
        PrgState state = repository.getCrtPrg();
        repository.printPrgState(state);

        while (!state.getExeStack().isEmpty()){
            try {
                oneStep(state);
                repository.printPrgState(state);
            }
            catch (CustomException | ADTException | StmtException | ExprException ex){
                throw new CustomException(ex.getMessage());
            }
        }
    }

}
