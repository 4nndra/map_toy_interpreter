package interpreter.model.exp;

import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VarExpTest {

    @Test
    void testEvaluateDefinedVariable() throws MyException {
        MyDictionary<String, IValue> symTable = new MyDictionary<>();
        symTable.put("x", new IntValue(10));

        VarExp exp = new VarExp("x");

        assertEquals(new IntValue(10), exp.eval(symTable));
    }

    @Test
    void testUndefinedVariable() {
        MyDictionary<String, IValue> symTable = new MyDictionary<>();
        VarExp exp = new VarExp("x");

        assertThrows(
                MyException.class,
                () -> exp.eval(symTable)
        );
    }
}
