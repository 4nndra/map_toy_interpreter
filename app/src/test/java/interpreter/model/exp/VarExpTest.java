package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VarExpTest {

    private MyDictionary<String, IValue> createSymTable() {
        return new MyDictionary<>();
    }

    @Test
    void testEvaluateDefinedVariable() throws MyException {
        MyDictionary<String, IValue> symTable = createSymTable();
        symTable.put("x", new IntValue(10));

        VarExp exp = new VarExp("x");

        assertEquals(new IntValue(10), exp.eval(symTable));
    }

    @Test
    void testUndefinedVariable() {
        VarExp exp = new VarExp("missing");

        assertThrows(
                ExpressionException.class,
                () -> exp.eval(createSymTable())
        );
    }
}