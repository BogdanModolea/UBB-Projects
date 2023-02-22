import controller.Controller;
import model.ADT.CustomList;
import model.ADT.CustomMap;
import model.ADT.CustomStack;
import model.ADT.ICustomStack;
import model.PrgState;
import model.exceptions.CustomException;
import model.expression.ArithExp;
import model.expression.ValueExp;
import model.expression.VarExp;
import model.statement.*;
import model.type.BoolType;
import model.type.IntType;
import model.type.StringType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;
import repository.IRepo;
import repository.Repository;
import view.ExitCommand;
import view.RunExample;
import view.TextMenu;

import java.io.BufferedReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, CustomException {
        ICustomStack<IStmt> stack1 = new CustomStack<>();
        ICustomStack<IStmt> stack2 = new CustomStack<>();
        ICustomStack<IStmt> stack3 = new CustomStack<>();
        ICustomStack<IStmt> stack4 = new CustomStack<>();

        IStmt example1 = new CompStmt(
                new VarDeclStmt("x", new IntType()),
                new CompStmt(
                        new AssignStmt("x", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("x"))
                )
        );

        PrgState prgState1 = new PrgState(stack1, new CustomMap<String, Value>(), new CustomList<Value>(), new CustomMap<StringValue, BufferedReader>(), example1);
        IRepo repo1 = new Repository(prgState1, "log1.txt");
        Controller controller1 = new Controller(repo1);



        IStmt example2 = new CompStmt(
                new VarDeclStmt("a", new IntType()),
                new CompStmt(
                        new VarDeclStmt("b",new IntType()),
                        new CompStmt(
                                new AssignStmt("a", new ArithExp(new ValueExp(new IntValue(2)),
                                        new ArithExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)), '*'), '+')),
                                new CompStmt(new AssignStmt("b",new ArithExp(
                                        new VarExp("a"), new ValueExp(new IntValue(1)), '+')),
                                        new PrintStmt(new VarExp("b"))))));

        PrgState prgState2 = new PrgState(stack2, new CustomMap<String, Value>(), new CustomList<Value>(), new CustomMap<StringValue, BufferedReader>(), example2);
        IRepo repo2 = new Repository(prgState2, "log2.txt");
        Controller controller2 = new Controller(repo2);



        IStmt example3 = new CompStmt(
                new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v",
                        new IntType()),
                        new CompStmt(
                                new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),
                                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                                        new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                        new PrintStmt(new VarExp("v"))))));

        PrgState prgState3 = new PrgState(stack3, new CustomMap<String, Value>(), new CustomList<Value>(), new CustomMap<StringValue, BufferedReader>(), example3);
        IRepo repo3 = new Repository(prgState3, "log3.txt");
        Controller controller3 = new Controller(repo3);



        IStmt example4 = new CompStmt(
                new VarDeclStmt("fileName", new StringType()),
                new CompStmt(new AssignStmt("fileName", new ValueExp(new StringValue("test.txt"))),
                        new CompStmt(new OpenRFileStmt(new VarExp("fileName")),
                                new CompStmt(new VarDeclStmt("x", new IntType()),
                                        new CompStmt(new ReadFileStmt(new VarExp("fileName"), "x"),
                                                new CompStmt(new PrintStmt(new VarExp("x")),
                                                        new CompStmt(new ReadFileStmt(new VarExp("fileName"), "x"),
                                                                new CompStmt(new PrintStmt(new VarExp("x")),
                                                                        new CloseRFileStmt(new VarExp("fileName"))))))))));

        PrgState prgState4 = new PrgState(stack4, new CustomMap<String, Value>(), new CustomList<Value>(), new CustomMap<StringValue, BufferedReader>(), example4);
        IRepo repo4 = new Repository(prgState4, "log4.txt");
        Controller controller4 = new Controller(repo4);


//        repo1.addState(prgState1);  //controller1.allStep();
//        repo2.addState(prgState2);
//        repo3.addState(prgState3);
//        repo4.addState(prgState4);

//        TextMenu menu = new TextMenu();
//        menu.addCommand(new ExitCommand("0", "exit"));
//        menu.addCommand(new RunExample("1", example1.toString(), controller1));
//        menu.addCommand(new RunExample("2", example2.toString(), controller2));
//        menu.addCommand(new RunExample("3", example3.toString(), controller3));
//        menu.addCommand(new RunExample("4", example4.toString(), controller4));
//        menu.show();

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1",example1.toString(),example1));
        menu.addCommand(new RunExample("2",example2.toString(),example2));//other prg
        menu.addCommand(new RunExample("3",example3.toString(),example3));//other prg
        menu.addCommand(new RunExample("4",example4.toString(),example4));//other prg
        menu.show();


    }
}