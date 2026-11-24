package interpreter.view;

import interpreter.model.type.StringType;
import interpreter.model.value.StringValue;
import interpreter.model.PrgState;
import interpreter.model.exp.*;
import interpreter.model.stmt.*;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IntValue;
import interpreter.model.value.BoolValue;
import interpreter.model.value.RefValue;

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

    // Demonstrates allocating an integer and a reference to that reference.
    public static PrgState example6() {
        IStmt program = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new NewStmt("a", new VarExp("v")),
                                        new CompStmt(
                                                new PrintStmt(new VarExp("v")),
                                                new PrintStmt(new VarExp("a"))
                                        )
                                )
                        )
                )
        );
        return createState(program);
    }

    // Demonstrates reading through nested heap references and adding a number.
    public static PrgState example7() {
        IStmt program = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new NewStmt("a", new VarExp("v")),
                                        new CompStmt(
                                                new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                                new PrintStmt(new ArithExp(
                                                        ArithOp.ADD,
                                                        new ReadHeapExp(new ReadHeapExp(new VarExp("a"))),
                                                        new ValueExp(new IntValue(5))
                                                ))
                                        )
                                )
                        )
                )
        );
        return createState(program);
    }

    // Demonstrates writing a new value through a reference.
    public static PrgState example8() {
        IStmt program = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                new CompStmt(
                                        new WriteHeapStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp(
                                                ArithOp.ADD,
                                                new ReadHeapExp(new VarExp("v")),
                                                new ValueExp(new IntValue(5))
                                        ))
                                )
                        )
                )
        );
        return createState(program);
    }

    // Demonstrates safe collection preserving a value reached through another reference.
    public static PrgState example9() {
        IStmt program = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new NewStmt("a", new VarExp("v")),
                                        new CompStmt(
                                                new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ReadHeapExp(
                                                        new ReadHeapExp(new VarExp("a"))
                                                ))
                                        )
                                )
                        )
                )
        );
        return createState(program);
    }

    // Demonstrates a while loop that counts down to zero.
    public static PrgState example10() {
        IStmt program = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(
                                new WhileStmt(
                                        new RelationalExp(
                                                RelOp.GT,
                                                new VarExp("v"),
                                                new ValueExp(new IntValue(0))
                                        ),
                                        new CompStmt(
                                                new PrintStmt(new VarExp("v")),
                                                new AssignStmt("v", new ArithExp(
                                                        ArithOp.SUB,
                                                        new VarExp("v"),
                                                        new ValueExp(new IntValue(1))
                                                ))
                                        )
                                ),
                                new PrintStmt(new VarExp("v"))
                        )
                )
        );
        return createState(program);
    }

    private static PrgState createState(IStmt program) {
        return PrgState.of(program);
    }
}
