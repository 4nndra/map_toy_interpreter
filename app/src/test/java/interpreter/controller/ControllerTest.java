package interpreter.controller;

import interpreter.Examples;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.NopStmt;
import interpreter.repository.Repository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @Test
    void example1PrintsTwo() throws MyException {
        PrgState state = Examples.example1();
        Controller controller = new Controller(new Repository(state));

        controller.allStep();

        assertTrue(state.getExeStack().isEmpty());
        assertEquals("2", state.getOut().get(0).toString());
    }

    @Test
    void example2PrintsEighteen() throws MyException {
        PrgState state = Examples.example2();
        Controller controller = new Controller(new Repository(state));

        controller.allStep();

        assertTrue(state.getExeStack().isEmpty());
        assertEquals("18", state.getOut().get(0).toString());
    }

    @Test
    void example3PrintsTwo() throws MyException {
        PrgState state = Examples.example3();
        Controller controller = new Controller(new Repository(state));

        controller.allStep();

        assertTrue(state.getExeStack().isEmpty());
        assertEquals("2", state.getOut().get(0).toString());
    }

    @Test
    void oneStepOnEmptyStackThrows() {
        PrgState state = new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                new NopStmt()
        );

        // The constructor puts NopStmt on the stack; remove it.
        assertDoesNotThrow(() -> state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());

        Controller controller = new Controller(new Repository(state));

        assertThrows(MyException.class, controller::oneStep);
    }
}
