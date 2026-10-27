package interpreter;

import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.*;
import interpreter.model.stmt.*;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.IValue;

public class Examples {

    public static PrgState example1() {
        IStmt program = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new interpreter.model.value.IntValue(2))),
                        new PrintStmt(new VarExp("v"))
                )
        );

        return new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                program
        );
    }

    public static PrgState example2() {
        IStmt program = new CompStmt(
                new VarDeclStmt("a", new IntType()),
                new CompStmt(
                        new AssignStmt("a", new ArithExp(
                                ArithOp.ADD,
                                new ValueExp(new interpreter.model.value.IntValue(10)),
                                new ValueExp(new interpreter.model.value.IntValue(8))
                        )),
                        new PrintStmt(new VarExp("a"))
                )
        );

        return new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                program
        );
    }

    public static PrgState example3() {
        IStmt program = new CompStmt(
                new VarDeclStmt("b", new BoolType()),
                new CompStmt(
                        new AssignStmt("b", new LogicExp(
                                LogicOp.AND,
                                new ValueExp(new interpreter.model.value.BoolValue(true)),
                                new ValueExp(new interpreter.model.value.BoolValue(true))
                        )),
                        new IfStmt(
                                new VarExp("b"),
                                new PrintStmt(new ValueExp(new interpreter.model.value.IntValue(2))),
                                new PrintStmt(new ValueExp(new interpreter.model.value.IntValue(0)))
                        )
                )
        );

        return new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                program
        );
    }
}
