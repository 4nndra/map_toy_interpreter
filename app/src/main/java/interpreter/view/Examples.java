package interpreter.view;

import interpreter.model.type.StringType;
import interpreter.model.value.StringValue;
import interpreter.model.PrgState;
import interpreter.model.exp.*;
import interpreter.model.stmt.*;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.model.value.BoolValue;

public class Examples {

    public static PrgState example1() {
        IStmt program = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))
                )
        );

        return createState(program);
    }

    public static PrgState example2() {
        IStmt program = new CompStmt(
                new VarDeclStmt("a", new IntType()),
                new CompStmt(
                        new VarDeclStmt("b", new IntType()),
                        new CompStmt(
                                new AssignStmt("a", new ArithExp(
                                        ArithOp.ADD,
                                        new ValueExp(new IntValue(2)),
                                        new ArithExp(
                                                ArithOp.MUL,
                                                new ValueExp(new IntValue(3)),
                                                new ValueExp(new IntValue(5))
                                        )
                                )),
                                new CompStmt(
                                        new AssignStmt("b", new ArithExp(
                                                ArithOp.ADD,
                                                new VarExp("a"),
                                                new ValueExp(new IntValue(1))
                                        )),
                                        new PrintStmt(new VarExp("b"))
                                )
                        )
                )
        );

        return createState(program);
    }

    public static PrgState example3() {
        IStmt program = new CompStmt(
                new VarDeclStmt("a", new BoolType()),
                new CompStmt(
                        new VarDeclStmt("v", new IntType()),
                        new CompStmt(
                                new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(
                                        new IfStmt(
                                                new VarExp("a"),
                                                new AssignStmt("v", new ValueExp(new IntValue(2))),
                                                new AssignStmt("v", new ValueExp(new IntValue(3)))
                                        ),
                                        new PrintStmt(new VarExp("v"))
                                )
                        )
                )
        );

        return createState(program);
    }

    public static IStmt fileExample(String fileName) {
        return new CompStmt(
                new VarDeclStmt("varf", new StringType()),
                new CompStmt(
                        new AssignStmt("varf",
                                new ValueExp(new StringValue(fileName))),
                        new CompStmt(
                                new OpenRFileStmt(new VarExp("varf")),
                                new CompStmt(
                                        new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(
                                                new ReadFileStmt(new VarExp("varf"), "varc"),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(
                                                                new ReadFileStmt(new VarExp("varf"), "varc"),
                                                                new CompStmt(
                                                                        new PrintStmt(new VarExp("varc")),
                                                                        new CloseRFileStmt(new VarExp("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    public static PrgState example4() {
        return createState(fileExample("test.in"));
    }

    public static PrgState example5() {
        IStmt program = new CompStmt(
                new VarDeclStmt("a", new IntType()),
                new CompStmt(
                        new VarDeclStmt("b", new IntType()),
                        new CompStmt(
                                new AssignStmt("a", new ValueExp(new IntValue(5))),
                                new CompStmt(
                                        new AssignStmt("b", new ValueExp(new IntValue(7))),
                                        new IfStmt(
                                                new RelationalExp(
                                                        RelOp.LT,
                                                        new VarExp("a"),
                                                        new VarExp("b")
                                                ),
                                                new PrintStmt(new VarExp("a")),
                                                new PrintStmt(new VarExp("b"))
                                        )
                                )
                        )
                )
        );

        return createState(program);
    }

    private static PrgState createState(IStmt program) {
        return PrgState.of(program);
    }
}
