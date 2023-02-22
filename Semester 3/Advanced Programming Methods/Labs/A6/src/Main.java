import model.ADT.CustomMap;
import model.exceptions.ExprException;
import model.exceptions.StmtException;
import model.expression.*;
import model.statement.*;
import model.type.*;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;
import view.ExitCommand;
import view.RunExample;
import view.TextMenu;


public class Main {
    public static void main(String[] args) throws ExprException, StmtException {

        IStmt example1 = new CompStmt(
                new VarDeclStmt("x", new IntType()),
                new CompStmt(
                        new AssignStmt("x", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("x"))
                )
        );
        example1.typecheck(new CustomMap<String, Type>());

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
        example2.typecheck(new CustomMap<String, Type>());


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
        example3.typecheck(new CustomMap<String, Type>());


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
        example4.typecheck(new CustomMap<String, Type>());


        IStmt example5 = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewHeapStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewHeapStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                                new PrintStmt(new ArithExp(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))),
                                                        new ValueExp(new IntValue(5)),
                                                        '+'))))))
        );
        example5.typecheck(new CustomMap<String, Type>());

        IStmt example6 = new CompStmt(
                new VarDeclStmt("x", new IntType()),
                new CompStmt(new AssignStmt("x", new ValueExp(new IntValue(10))),
                        new CompStmt(new WhileStmt(new RelationalExp(new VarExp("x"), new ValueExp(new IntValue(0)), 5), new CompStmt(new PrintStmt(new VarExp("x")), new AssignStmt("x", new ArithExp(new VarExp("x"), new ValueExp(new IntValue(1)), '-')))),
                                new PrintStmt(new VarExp("x"))))
        );
        example6.typecheck(new CustomMap<String, Type>());

        IStmt example7 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(new NewHeapStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(new ForkStmt(new CompStmt(new WriteHeapStmt("a", new ValueExp(new IntValue(30))),
                                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                                new PrintStmt(new ReadHeapExp(new VarExp("a"))))))),
                                                new CompStmt(new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new ReadHeapExp(new VarExp("a")))))))));
        example7.typecheck(new CustomMap<String, Type>());


        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1",example1.toString(),example1));
        menu.addCommand(new RunExample("2",example2.toString(),example2)); //other prg
        menu.addCommand(new RunExample("3",example3.toString(),example3)); //other prg
        menu.addCommand(new RunExample("4",example4.toString(),example4)); //other prg
        menu.addCommand(new RunExample("5",example5.toString(),example5)); //other prg
        menu.addCommand(new RunExample("6",example6.toString(),example6)); //other prg
        menu.addCommand(new RunExample("7",example7.toString(),example7)); //other prg
        menu.show();


    }
}