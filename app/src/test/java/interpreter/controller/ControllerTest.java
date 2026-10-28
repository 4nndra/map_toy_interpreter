package interpreter.controller;

import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ControllerTest {

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
    void oneStepOnEmptyStackThrows() throws Exception {
        PrgState state = Examples.example1();
        state.getExeStack().pop();

        Controller controller = new Controller(new Repository(state));

        assertThrows(
                StatementException.class,
                () -> controller.oneStep(state)
        );
    }

    private PrgState runExample(PrgState state) throws Exception {
        Controller controller = new Controller(new Repository(state));
        controller.allStep();
        return state;
    }
}
