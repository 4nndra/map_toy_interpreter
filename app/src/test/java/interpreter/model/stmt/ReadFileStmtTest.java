package interpreter.model.stmt;

import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.IntType;
import interpreter.model.type.StringType;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ReadFileStmtTest {

    @TempDir
    Path tempDir;

    @Test
    void testReadInteger() throws Exception {
        Path file = tempDir.resolve("test.in");
        Files.writeString(file, "123\n456");

        PrgState state = PrgState.of(new NopStmt());
        StringValue fileName = new StringValue(file.toString());

        state.getSymTable().put("x", new IntValue(0));

        new OpenRFileStmt(new ValueExp(fileName)).execute(state);
        new ReadFileStmt(new ValueExp(fileName), "x").execute(state);

        assertEquals(new IntValue(123), state.getSymTable().lookup("x"));

        state.getFileTable().lookup(fileName).close();
    }

    @Test
    void testReadAtEndOfFile() throws Exception {
        Path file = tempDir.resolve("test.in");
        Files.writeString(file, "123");

        PrgState state = PrgState.of(new NopStmt());
        StringValue fileName = new StringValue(file.toString());

        state.getSymTable().put("x", new IntValue(0));

        new OpenRFileStmt(new ValueExp(fileName)).execute(state);
        new ReadFileStmt(new ValueExp(fileName), "x").execute(state);
        new ReadFileStmt(new ValueExp(fileName), "x").execute(state);

        assertEquals(new IntValue(0), state.getSymTable().lookup("x"));

        state.getFileTable().lookup(fileName).close();
    }

    @Test
    void testReadIntoUndeclaredVariable() {
        PrgState state = PrgState.of(new NopStmt());

        assertThrows(StatementException.class,
                () -> new ReadFileStmt(
                        new ValueExp(new StringValue("test.in")), "x"
                ).execute(state));
    }

    @Test
    void testReadIntoNonIntegerVariable() {
        PrgState state = PrgState.of(new NopStmt());
        state.getSymTable().put("x", new StringValue("hello"));

        assertThrows(StatementException.class,
                () -> new ReadFileStmt(
                        new ValueExp(new StringValue("test.in")), "x"
                ).execute(state));
    }

    @Test
    void testReadFromClosedFile() {
        PrgState state = PrgState.of(new NopStmt());
        state.getSymTable().put("x", new IntValue(0));

        assertThrows(StatementException.class,
                () -> new ReadFileStmt(
                        new ValueExp(new StringValue("test.in")), "x"
                ).execute(state));
    }

    @Test
    void testReadInvalidInteger() throws Exception {
        Path file = tempDir.resolve("test.in");
        Files.writeString(file, "hello");

        PrgState state = PrgState.of(new NopStmt());
        StringValue fileName = new StringValue(file.toString());

        state.getSymTable().put("x", new IntValue(0));
        new OpenRFileStmt(new ValueExp(fileName)).execute(state);

        assertThrows(StatementException.class,
                () -> new ReadFileStmt(
                        new ValueExp(fileName), "x"
                ).execute(state));

        state.getFileTable().lookup(fileName).close();
    }
}
