package interpreter.model.exp;

import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogicExpTest {

    private MyDictionary<String, IValue> createSymTable() {
        return new MyDictionary<>();
    }

    @Test
    void testAndTrue() throws MyException {
        LogicExp exp = new LogicExp(
                LogicOp.AND,
                new ValueExp(new BoolValue(true)),
                new ValueExp(new BoolValue(true))
        );

        assertEquals(new BoolValue(true), exp.eval(createSymTable()));
    }

    @Test
    void testAndFalse() throws MyException {
        LogicExp exp = new LogicExp(
                LogicOp.AND,
                new ValueExp(new BoolValue(true)),
                new ValueExp(new BoolValue(false))
        );

        assertEquals(new BoolValue(false), exp.eval(createSymTable()));
    }

    @Test
    void testOrTrue() throws MyException {
        LogicExp exp = new LogicExp(
                LogicOp.OR,
                new ValueExp(new BoolValue(false)),
                new ValueExp(new BoolValue(true))
        );

        assertEquals(new BoolValue(true), exp.eval(createSymTable()));
    }

    @Test
    void testOrFalse() throws MyException {
        LogicExp exp = new LogicExp(
                LogicOp.OR,
                new ValueExp(new BoolValue(false)),
                new ValueExp(new BoolValue(false))
        );

        assertEquals(new BoolValue(false), exp.eval(createSymTable()));
    }

    @Test
    void testNonBooleanOperand() {
        LogicExp exp = new LogicExp(
                LogicOp.AND,
                new ValueExp(new IntValue(1)),
                new ValueExp(new BoolValue(true))
        );

        assertThrows(
                MyException.class,
                () -> exp.eval(createSymTable())
        );
    }
}
