package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NopStmtTest {

    @Test
    void testExecuteDoesNothing() throws MyException {
        PrgState state = PrgState.of(new NopStmt());

        assertEquals(1, state.getExeStack().isEmpty() ? 0 : 1);

        state.getExeStack().pop();
        NopStmt stmt = new NopStmt();

        assertDoesNotThrow(() -> stmt.execute(state));
        assertTrue(state.getExeStack().isEmpty());
    }
}
