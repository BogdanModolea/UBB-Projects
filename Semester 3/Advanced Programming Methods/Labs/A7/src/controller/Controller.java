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
import model.type.Type;
import model.value.IntValue;
import model.value.RefValue;
import model.value.Value;
import repository.IRepo;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    private IRepo repository;
    private ExecutorService executor = Executors.newFixedThreadPool(2);

    public Controller(IRepo repo) {
        this.repository = repo;
    }


    public IRepo getRepository(){
        return repository;
    }

    Map<Integer, Value> safeGarbageCollector(List<Integer> addresses, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(elem -> addresses.contains(elem.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    void garbageCollector(List<PrgState> prgList) {
        List<Integer> adrs = Objects.requireNonNull(prgList.stream()
                        .map(p -> getAddrFromSymTable(
                                p.getSymTable().getContent().values(),
                                p.getHeap().getContent().values()))
                        .map(Collection::stream)
                        .reduce(Stream::concat).orElse(null))
                        .collect(Collectors.toList());

        prgList.forEach(
                programState -> {
                    programState.getHeap().setContent(
                            safeGarbageCollector(adrs, prgList.get(0).getHeap().getContent())
                    );
                }
        );
    }


    public void oneStepForAllPrograms(List<PrgState> programList) throws CustomException, IOException {
//        programList.forEach(prg -> {
//            try {
//                repository.printPrgState(prg);
//            } catch (CustomException | IOException e) {
//                e.printStackTrace();
//            }
//        });

        List<Callable<PrgState>> callableList = programList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(() -> {return p.oneStep();}))
                .collect(Collectors.toList());

        List<PrgState> newPrgList = null;
        try {
            newPrgList = executor.invokeAll(callableList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (ExecutionException | InterruptedException e) {

                        }
                        return null;
                    })
                    .filter(p -> p != null)
                    .collect(Collectors.toList());


        } catch (InterruptedException e) {
            throw new CustomException(e.getMessage());
        }

        programList.addAll(newPrgList);

        
        programList.forEach(prg -> {
            try {
                repository.printPrgState(prg);
            } catch (CustomException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        repository.setPrgList(programList);
    }


    public void allStep() throws CustomException, IOException, InterruptedException {
        List<PrgState> prgList = removeCompletedPrograms(repository.getPrgList());
        repository.printPrgState(prgList.get(0));
        while (prgList.size() > 0) {
            garbageCollector(prgList);
            prgList = removeCompletedPrograms(repository.getPrgList());
            prgList = removeDuplicateStates(prgList);
            oneStepForAllPrograms(prgList);
        }

        oneStepForAllPrograms(prgList);
        prgList = removeCompletedPrograms(repository.getPrgList());
        prgList = removeDuplicateStates(prgList);


        executor.shutdownNow();

        repository.setPrgList(prgList);
    }

    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues, Collection<Value> heap) {
        return Stream.concat(
                        heap.stream()
                                .filter(v -> v instanceof RefValue)
                                .map(v -> {
                                    RefValue v1 = (RefValue) v;
                                    return v1.getAddress();
                                })
                        , symTableValues.stream()
                                .filter(v -> v instanceof RefValue)
                                .map(v -> {
                                    RefValue v1 = (RefValue) v;
                                    return v1.getAddress();
                                })
                )
                .collect(Collectors.toList());
    }

    List<PrgState> removeCompletedPrograms(List<PrgState> prgList){
        return prgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    public List<PrgState> removeDuplicateStates(List<PrgState> prgList) {
        return prgList.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    public void typecheckOriginalProgram() throws StmtException, ExprException{
        CustomMap<String, Type> typeEnviroment = new CustomMap<String, Type>();
        IStmt originalProgram = repository.getOriginalProgram();
        originalProgram.typecheck(typeEnviroment);
    }

    public void executeOneStep() throws CustomException, InterruptedException, IOException {
        List<PrgState> list = removeCompletedPrograms(repository.getPrgList());
        garbageCollector(list);
        oneStepForAllPrograms(list);
        list = removeCompletedPrograms(repository.getPrgList());
        repository.setPrgList(list);

        if(list.isEmpty())
            executor.shutdownNow();
    }


    @Override
    public String toString(){
        return repository.getOriginalProgram().toString();
    }
}
