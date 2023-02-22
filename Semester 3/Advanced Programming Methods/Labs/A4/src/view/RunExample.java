package view;

import controller.Controller;
import model.ADT.CustomHeap;
import model.ADT.CustomList;
import model.ADT.CustomMap;
import model.ADT.CustomStack;
import model.PrgState;
import model.exceptions.CustomException;
import model.statement.IStmt;
import model.value.StringValue;
import model.value.Value;
import repository.IRepo;
import repository.Repository;

import java.io.BufferedReader;
import java.io.IOException;

public class RunExample extends Command {
//    private Controller controller;
//
//    public RunExample(String key, String description, Controller controller) {
//        super(key, description);
//        this.controller = controller;
//    }

    private IStmt originalProgram;

    public RunExample(String key, String desc, IStmt originalProgram) {
        super(key, desc);
        this.originalProgram = originalProgram;
    }

    @Override
    public void execute() {
        try {
            CustomStack<IStmt> stack = new CustomStack<>();
            CustomHeap<Value> heap = new CustomHeap<>();
            CustomMap<String, Value> symTable = new CustomMap<>();
            CustomList<Value> out = new CustomList<>();
            CustomMap<StringValue, BufferedReader> br = new CustomMap<>();

            PrgState prgState = new PrgState(stack, symTable, out, br, heap, originalProgram);
            IRepo repo = new Repository(prgState, "log.txt");
            Controller controller = new Controller(repo);

            controller.allStep();
        } catch (CustomException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
