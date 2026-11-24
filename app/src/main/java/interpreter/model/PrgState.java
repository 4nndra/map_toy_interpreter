package interpreter.model;

import interpreter.model.adt.IStack;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IList;
import interpreter.model.adt.MyStack;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.FileTable;
import interpreter.model.adt.IHeap;
import interpreter.model.adt.MyHeap;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IValue;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.util.TreeMap;

public class PrgState {
    private final IStack<IStmt> exeStack;
    private final IDictionary<String, IValue> symTable;
    private final IList<IValue> out;
    private final FileTable fileTable;
    private final IHeap heap;
    private final IStmt originalProgram;

    // A program state groups the execution structures, including open files and heap.
    public PrgState(IStack<IStmt> exeStack,
                    IDictionary<String, IValue> symTable,
                    IList<IValue> out,
                    FileTable fileTable,
                    IHeap heap,
                    IStmt originalProgram) {

        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.originalProgram = originalProgram;

        // Always begin execution with the original program on the execution stack.
        this.exeStack.push(originalProgram);
    }

    // Preserves the previous constructor while creating an empty heap.
    public PrgState(IStack<IStmt> exeStack,
                    IDictionary<String, IValue> symTable,
                    IList<IValue> out,
                    FileTable fileTable,
                    IStmt originalProgram) {
        this(exeStack, symTable, out, fileTable, new MyHeap(), originalProgram);
    }

    // Compatibility overload for repositories or tests using a plain dictionary.
    public PrgState(IStack<IStmt> exeStack,
                    IDictionary<String, IValue> symTable,
                    IList<IValue> out,
                    IDictionary<StringValue, BufferedReader> fileTable,
                    IStmt originalProgram) {
        this(exeStack, symTable, out, copyFileTable(fileTable), originalProgram);
    }

    // Converts a general dictionary into the typed FileTable used by program states.
    private static FileTable copyFileTable(IDictionary<StringValue, BufferedReader> source) {
        FileTable result = new FileTable();
        source.toMap().forEach(result::put);
        return result;
    }

    // Creates a fresh program state initialized for the given program.
    public static PrgState of(IStmt program) {
        return new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                new FileTable(),
                new MyHeap(),
                program
        );
    }

    // Returns the exe stack.
    public IStack<IStmt> getExeStack() {
        return exeStack;
    }

    // Returns the sym table.
    public IDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    // Returns the out.
    public IList<IValue> getOut() {
        return out;
    }

    // Returns the file table.
    public FileTable getFileTable() {
        return fileTable;
    }

    // Returns the program's heap.
    public IHeap getHeap() {
        return heap;
    }

    // Returns the original program.
    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    // Formats the execution stack, symbol table, output and file table for logging.
    public String toLogString() {
        StringBuilder sb = new StringBuilder();

        sb.append("ExeStack:\n");
        exeStack.toList().forEach(s ->
                sb.append(s).append('\n'));

        sb.append("SymTable:\n");
        symTable.toMap().forEach((key, value) ->
                sb.append(key).append(" --> ")
                        .append(value).append('\n'));

        sb.append("Out:\n");
        out.toList().forEach(value ->
                sb.append(value).append('\n'));

        sb.append("FileTable:\n");
        fileTable.toMap().keySet().forEach(file ->
                sb.append(file.getValue()).append('\n'));

        sb.append("Heap:\n");
        new TreeMap<>(heap.getContent()).forEach((address, value) ->
                sb.append(address).append(" --> ").append(value).append('\n'));

        return sb.toString();
    }

    @Override
    // Returns a readable representation of this object.
    public String toString() {
        // Formats the execution stack, symbol table, output and file table for logging.
        return toLogString();
    }
}
