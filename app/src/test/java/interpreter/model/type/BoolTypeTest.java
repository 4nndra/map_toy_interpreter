package interpreter.model.type;

import interpreter.model.value.BoolValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoolTypeTest {

    @Test
    void defaultValueIsFalse() {
        BoolType type = new BoolType();

        assertEquals(new BoolValue(false), type.defaultValue());
    }

    @Test
    void boolTypesAreEqual() {
        assertEquals(new BoolType(), new BoolType());
    }

    @Test
    void toStringReturnsBool() {
        assertEquals("bool", new BoolType().toString());
    }
}
