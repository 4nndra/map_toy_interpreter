package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelationalExpTest {

    private BoolValue evaluate(RelOp op, int a, int b)
            throws Exception {
        RelationalExp exp = new RelationalExp(
                op,
                new ValueExp(new IntValue(a)),
                new ValueExp(new IntValue(b))
        );

        return (BoolValue) exp.eval(new MyDictionary<>());
    }

    @Test
    void testsAllRelationalOperators() throws Exception {
        assertTrue(evaluate(RelOp.LT, 2, 5).getValue());
        assertFalse(evaluate(RelOp.LT, 5, 2).getValue());

        assertTrue(evaluate(RelOp.LE, 2, 5).getValue());
        assertFalse(evaluate(RelOp.LE, 5, 2).getValue());

        assertTrue(evaluate(RelOp.EQ, 5, 5).getValue());
        assertFalse(evaluate(RelOp.EQ, 5, 2).getValue());

        assertTrue(evaluate(RelOp.NE, 5, 2).getValue());
        assertFalse(evaluate(RelOp.NE, 5, 5).getValue());

        assertTrue(evaluate(RelOp.GT, 5, 2).getValue());
        assertFalse(evaluate(RelOp.GT, 2, 5).getValue());

        assertTrue(evaluate(RelOp.GE, 5, 2).getValue());
        assertFalse(evaluate(RelOp.GE, 2, 5).getValue());
    }

    @Test
    void nonIntegerOperandThrowsExpressionException() {
        RelationalExp exp = new RelationalExp(
                RelOp.LT,
                new ValueExp(new StringValue("hello")),
                new ValueExp(new IntValue(5))
        );

        assertThrows(
                ExpressionException.class,
                () -> exp.eval(new MyDictionary<>())
        );
    }
}
