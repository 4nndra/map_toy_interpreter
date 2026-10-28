package interpreter.model.type;

import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntTypeTest {

    @Test
    void defaultValueIsZero() {
        IntType type = new IntType();

        assertEquals(new IntValue(0), type.defaultValue());
    }

    @Test
    void intTypesAreEqual() {
        assertEquals(new IntType(), new IntType());
    }

    @Test
    void toStringReturnsInt() {
        assertEquals("int", new IntType().toString());
    }
}
