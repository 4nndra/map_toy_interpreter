package interpreter.controller;

import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
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

    // Test: verifies Example 6 prints its reference values and builds the expected heap.
    @Test
    void example6PrintsReferencesAndBuildsHeap() throws Exception {
        PrgState state = runExample(Examples.example6());

        assertEquals(new RefValue(1, new IntType()), state.getOut().get(0));
        assertEquals(new RefValue(2, new RefType(new IntType())), state.getOut().get(1));
        assertEquals(new IntValue(20), state.getHeap().get(1));
        assertEquals(new RefValue(1, new IntType()), state.getHeap().get(2));
    }

    // Test: verifies Example 7 prints values read through references.
    @Test
    void example7PrintsTwentyAndTwentyFive() throws Exception {
        PrgState state = runExample(Examples.example7());

        assertEquals(new IntValue(20), state.getOut().get(0));
        assertEquals(new IntValue(25), state.getOut().get(1));
    }

    // Test: verifies Example 8 observes the value written to the heap.
    @Test
    void example8PrintsTwentyAndThirtyFive() throws Exception {
        PrgState state = runExample(Examples.example8());

        assertEquals(new IntValue(20), state.getOut().get(0));
        assertEquals(new IntValue(35), state.getOut().get(1));
        assertEquals(new IntValue(30), state.getHeap().get(1));
    }

    // Test: verifies Example 9 keeps the indirectly reachable first value.
    @Test
    void example9PrintsTwentyWithSafeCollection() throws Exception {
        PrgState state = runExample(Examples.example9());

        assertEquals(new IntValue(20), state.getOut().get(0));
        assertEquals(new IntValue(20), state.getHeap().get(1));
    }

    // Test: verifies Example 10 prints each countdown value through zero.
    @Test
    void example10PrintsCountdownToZero() throws Exception {
        PrgState state = runExample(Examples.example10());

        assertEquals(
                java.util.List.of(
                        new IntValue(4),
                        new IntValue(3),
                        new IntValue(2),
                        new IntValue(1),
                        new IntValue(0)
                ),
                state.getOut().toList()
        );
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
