package interpreter.model;

import interpreter.model.stmt.NopStmt;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Tests heap initialization and logging in a program state.
class PrgStateHeapTest {

    // Test: verifies the factory provides an empty heap.
    @Test
    void factoryCreatesStateWithEmptyHeap() {
        PrgState state = PrgState.of(new NopStmt());

        assertTrue(state.getHeap().getContent().isEmpty());
    }

    // Test: verifies the log includes heap values in sorted address order.
    @Test
    void logIncludesSortedHeapSection() {
        PrgState state = PrgState.of(new NopStmt());
        state.getHeap().allocate(new IntValue(10));
        state.getHeap().allocate(new IntValue(20));

        String log = state.toLogString();

        int fileTableSection = log.indexOf("FileTable:");
        int heapSection = log.indexOf("Heap:");
        int firstAddress = log.indexOf("1 --> 10");
        int secondAddress = log.indexOf("2 --> 20");

        assertTrue(fileTableSection >= 0);
        assertTrue(heapSection > fileTableSection);
        assertTrue(firstAddress > heapSection);
        assertTrue(secondAddress > firstAddress);
        assertEquals("Heap:\n1 --> 10\n2 --> 20\n", log.substring(heapSection));
    }
}
