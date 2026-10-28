package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;
import interpreter.exceptions.StatementException;

import static org.junit.jupiter.api.Assertions.*;

class IfStmtTest {

    private PrgState createState() {
        PrgState state = new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                new NopStmt()
        );

        // Remove the original NopStmt so the stack starts empty.
        try {
            state.getExeStack().pop();
        } catch (MyException e) {
            fail("Could not clear the execution stack");
        }

        return state;
    }

    @Test
    void testTrueConditionPushesThenBranch() throws MyException {
        PrgState state = createState();

        IStmt thenBranch = new NopStmt();
        IStmt elseBranch = new PrintStmt(
                new ValueExp(new BoolValue(false))
        );

        new IfStmt(
                new ValueExp(new BoolValue(true)),
                thenBranch,
                elseBranch
        ).execute(state);

        assertFalse(state.getExeStack().isEmpty());
        assertSame(thenBranch, state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void testFalseConditionPushesElseBranch() throws MyException {
        PrgState state = createState();

        IStmt thenBranch = new NopStmt();
        IStmt elseBranch = new NopStmt();

        new IfStmt(
                new ValueExp(new BoolValue(false)),
                thenBranch,
                elseBranch
        ).execute(state);

        assertFalse(state.getExeStack().isEmpty());
        assertSame(elseBranch, state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void testNonBooleanConditionThrows() {
        PrgState state = createState();

        IfStmt stmt = new IfStmt(
                new ValueExp(new IntValue(1)),
                new NopStmt(),
                new NopStmt()
        );

        assertThrows(
                StatementException.class,
                () -> stmt.execute(state)
        );
    }
}
