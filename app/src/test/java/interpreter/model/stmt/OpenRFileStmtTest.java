package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.StringValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class OpenRFileStmtTest {

    @TempDir
    Path tempDir;

    @Test
    void testOpenExistingFile() throws Exception {
        Path file = tempDir.resolve("test.in");
        Files.writeString(file, "123");

        PrgState state = PrgState.of(new NopStmt());
        StringValue fileName = new StringValue(file.toString());

        new OpenRFileStmt(new ValueExp(fileName)).execute(state);

        assertTrue(state.getFileTable().isDefined(fileName));
        assertNotNull(state.getFileTable().lookup(fileName));

        state.getFileTable().lookup(fileName).close();
    }

    @Test
    void testOpenSameFileTwice() throws Exception {
        Path file = tempDir.resolve("test.in");
        Files.writeString(file, "123");

        PrgState state = PrgState.of(new NopStmt());
        StringValue fileName = new StringValue(file.toString());

        new OpenRFileStmt(new ValueExp(fileName)).execute(state);

        assertThrows(StatementException.class,
                () -> new OpenRFileStmt(new ValueExp(fileName)).execute(state));

        state.getFileTable().lookup(fileName).close();
    }

    @Test
    void testOpenNonexistentFile() {
        Path file = tempDir.resolve("missing.in");

        PrgState state = PrgState.of(new NopStmt());

        assertThrows(StatementException.class,
                () -> new OpenRFileStmt(
                        new ValueExp(new StringValue(file.toString()))
                ).execute(state));
    }

    @Test
    void testFilenameMustBeString() {
        PrgState state = PrgState.of(new NopStmt());

        assertThrows(StatementException.class,
                () -> new OpenRFileStmt(
                        new ValueExp(new IntValue(10))
                ).execute(state));
    }
}
