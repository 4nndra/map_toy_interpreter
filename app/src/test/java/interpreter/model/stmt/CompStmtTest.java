package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompStmtTest {

    @Test
    void testExecutePushesStatementsInCorrectOrder() throws MyException {
        MyStack<IStmt> stack = new MyStack<>();

        PrgState state = new PrgState(
                stack,
                new MyDictionary<String, IValue>(),
                new MyList<>(),
                new NopStmt()
        );

        // Remove the initial program from the stack.
        state.getExeStack().pop();

        IStmt first = new NopStmt();
        IStmt second = new PrintStmt(
                new interpreter.model.exp.ValueExp(new IntValue(5))
        );

        new CompStmt(first, second).execute(state);

        // The first statement must execute before the second.
        assertSame(first, state.getExeStack().pop());
        assertSame(second, state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());
    }
}
