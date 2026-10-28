package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.value.IValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NopStmtTest {

    @Test
    void testExecuteDoesNothing() throws MyException {
        PrgState state = new PrgState(
                new MyStack<>(),
                new MyDictionary<String, IValue>(),
                new MyList<>(),
                new NopStmt()
        );

        assertEquals(1, state.getExeStack().isEmpty() ? 0 : 1);

        state.getExeStack().pop();
        NopStmt stmt = new NopStmt();

        assertDoesNotThrow(() -> stmt.execute(state));
        assertTrue(state.getExeStack().isEmpty());
    }
}
