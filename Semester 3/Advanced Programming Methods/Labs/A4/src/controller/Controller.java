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
import model.value.RefValue;
import model.value.Value;
import repository.IRepo;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                state.getHeap().setContent(unsafeGarbageCollector(
                        getAddrFromSymTable(state.getSymTable().getContent().values()), state.getHeap().getContent())
                );
            }
            catch (CustomException | ADTException | StmtException | ExprException ex){
                throw new CustomException(ex.getMessage());
            }
        }
    }

    Map<Integer, Value> unsafeGarbageCollector(List<Integer> addresses, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(elem -> addresses.contains(elem.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.getAddress();
                })
                .collect(Collectors.toList());
    }

}
