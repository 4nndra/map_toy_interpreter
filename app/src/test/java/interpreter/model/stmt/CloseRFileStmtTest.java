package interpreter.model.stmt;

import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class CloseRFileStmtTest {

    @TempDir
    Path tempDir;

    // Test: verifies test close open file.
    @Test
    void testCloseOpenFile() throws Exception {
        Path file = tempDir.resolve("test.in");
        Files.writeString(file, "123");

        PrgState state = PrgState.of(new NopStmt());
        StringValue fileName = new StringValue(file.toString());

        new OpenRFileStmt(new ValueExp(fileName)).execute(state);

        new CloseRFileStmt(new ValueExp(fileName)).execute(state);

        assertFalse(state.getFileTable().isDefined(fileName));
    }

    // Test: verifies test close file that is not open.
    @Test
    void testCloseFileThatIsNotOpen() {
        PrgState state = PrgState.of(new NopStmt());

        assertThrows(StatementException.class,
                () -> new CloseRFileStmt(
                        new ValueExp(new StringValue("missing.in"))
                ).execute(state));
    }

    // Test: verifies test filename must be string.
    @Test
    void testFilenameMustBeString() {
        PrgState state = PrgState.of(new NopStmt());

        assertThrows(StatementException.class,
                () -> new CloseRFileStmt(
                        new ValueExp(new IntValue(10))
                ).execute(state));
    }
}