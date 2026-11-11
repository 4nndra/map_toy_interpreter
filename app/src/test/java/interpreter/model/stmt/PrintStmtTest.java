package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrintStmtTest {

    private PrgState createState() {
        return PrgState.of(new NopStmt());
    }

    @Test
    void testPrintIntegerValue() throws MyException {
        PrgState state = createState();

        new PrintStmt(new ValueExp(new IntValue(42))).execute(state);

        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(42), state.getOut().get(0));
    }
}
