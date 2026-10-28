package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArithExpTest {

    private MyDictionary<String, IValue> createSymTable() {
        return new MyDictionary<>();
    }

    @Test
    void testAddition() throws MyException {
        ArithExp exp = new ArithExp(
                ArithOp.ADD,
                new ValueExp(new IntValue(2)),
                new ValueExp(new IntValue(3))
        );

        assertEquals(new IntValue(5), exp.eval(createSymTable()));
    }

    @Test
    void testSubtraction() throws MyException {
        ArithExp exp = new ArithExp(
                ArithOp.SUB,
                new ValueExp(new IntValue(7)),
                new ValueExp(new IntValue(3))
        );

        assertEquals(new IntValue(4), exp.eval(createSymTable()));
    }

    @Test
    void testMultiplication() throws MyException {
        ArithExp exp = new ArithExp(
                ArithOp.MUL,
                new ValueExp(new IntValue(4)),
                new ValueExp(new IntValue(3))
        );

        assertEquals(new IntValue(12), exp.eval(createSymTable()));
    }

    @Test
    void testDivision() throws MyException {
        ArithExp exp = new ArithExp(
                ArithOp.DIV,
                new ValueExp(new IntValue(8)),
                new ValueExp(new IntValue(2))
        );

        assertEquals(new IntValue(4), exp.eval(createSymTable()));
    }

    @Test
    void testDivisionByZero() {
        ArithExp exp = new ArithExp(
                ArithOp.DIV,
                new ValueExp(new IntValue(8)),
                new ValueExp(new IntValue(0))
        );

        assertThrows(
                ExpressionException.class,
                () -> exp.eval(createSymTable())
        );
    }

    @Test
    void testNonIntegerOperand() {
        ArithExp exp = new ArithExp(
                ArithOp.ADD,
                new ValueExp(new BoolValue(true)),
                new ValueExp(new IntValue(3))
        );

        assertThrows(
                ExpressionException.class,
                () -> exp.eval(createSymTable())
        );
    }
}