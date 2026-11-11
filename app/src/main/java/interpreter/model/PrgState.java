package interpreter.model;

import interpreter.model.adt.IStack;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IList;
import interpreter.model.adt.MyStack;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IValue;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;

public class PrgState {
    private final IStack<IStmt> exeStack;
    private final IDictionary<String, IValue> symTable;
    private final IList<IValue> out;
    private final IDictionary<StringValue, BufferedReader> fileTable;
    private final IStmt originalProgram;

    public PrgState(IStack<IStmt> exeStack,
                    IDictionary<String, IValue> symTable,
                    IList<IValue> out,
                    IDictionary<StringValue, BufferedReader> fileTable,
                    IStmt originalProgram) {

        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.originalProgram = originalProgram;

        this.exeStack.push(originalProgram);
    }

    public static PrgState of(IStmt program) {
        return new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                new MyDictionary<>(),
                program
        );
    }

    public IStack<IStmt> getExeStack() {
        return exeStack;
    }

    public IDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public IList<IValue> getOut() {
        return out;
    }

    public IDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public String toLogString() {
        return "ExeStack:\n" + exeStack
                + "\nSymTable:\n" + symTable
                + "\nOut:\n" + out
                + "\nFileTable:\n" + fileTable
                + "\n";
    }

    @Override
    public String toString() {
        return toLogString();
    }
}
