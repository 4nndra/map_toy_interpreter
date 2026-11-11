package interpreter.controller;

import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @TempDir
    Path tempDir;

    private PrgState runExample(PrgState state) throws Exception {
        String logPath = tempDir.resolve("test_log.txt").toString();
        Controller controller = new Controller(
                new Repository(state, logPath)
        );
        controller.allStep();
        return state;
    }

    @Test
    void example1PrintsTwo() throws Exception {
        PrgState state = runExample(Examples.example1());

        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(2), state.getOut().get(0));
    }

    @Test
    void example2PrintsEighteen() throws Exception {
        PrgState state = runExample(Examples.example2());

        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(18), state.getOut().get(0));
    }

    @Test
    void example3PrintsTwo() throws Exception {
        PrgState state = runExample(Examples.example3());

        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(2), state.getOut().get(0));
    }

    @Test
    void example4ReadsBothNumbersAndClosesFile() throws Exception {
        Path input = tempDir.resolve("test.in");
        Files.writeString(input, "15\n50\n");

        PrgState state = runExample(
                PrgState.of(Examples.fileExample(input.toString()))
        );

        assertEquals(new IntValue(15), state.getOut().get(0));
        assertEquals(new IntValue(50), state.getOut().get(1));
        assertTrue(state.getFileTable().toMap().isEmpty());
    }

    @Test
    void example4WithEmptyFilePrintsZeros() throws Exception {
        Path input = tempDir.resolve("empty.in");
        Files.writeString(input, "");

        PrgState state = runExample(
                PrgState.of(Examples.fileExample(input.toString()))
        );

        assertEquals(new IntValue(0), state.getOut().get(0));
        assertEquals(new IntValue(0), state.getOut().get(1));
        assertTrue(state.getFileTable().toMap().isEmpty());
    }

    @Test
    void example5PrintsFive() throws Exception {
        PrgState state = runExample(Examples.example5());

        assertEquals(new IntValue(5), state.getOut().get(0));
    }

    @Test
    void logContainsFourSectionsInCorrectOrder() throws Exception {
        String logPath = tempDir.resolve("log.txt").toString();

        Controller controller = new Controller(
                new Repository(Examples.example1(), logPath)
        );

        controller.allStep();

        String log = Files.readString(Path.of(logPath));

        int exeStack = log.indexOf("ExeStack:");
        int symTable = log.indexOf("SymTable:");
        int out = log.indexOf("Out:");
        int fileTable = log.indexOf("FileTable:");

        assertTrue(exeStack >= 0);
        assertTrue(exeStack < symTable);
        assertTrue(symTable < out);
        assertTrue(out < fileTable);
    }

    @Test
    void oneStepOnEmptyStackThrows() throws Exception {
        PrgState state = Examples.example1();
        state.getExeStack().pop();

        Controller controller = new Controller(
                new Repository(state, tempDir.resolve("empty.log").toString())
        );

        assertThrows(
                StatementException.class,
                () -> controller.oneStep(state)
        );
    }
}
