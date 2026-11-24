package interpreter.model.type;

import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

// Tests the recursive reference type and its null default value.
class RefTypeTest {

    // Test: verifies a reference variable gets the null reference default.
    @Test
    void refVariableGetsNullDefaultValue() {
        RefType type = new RefType(new IntType());

        assertEquals(new RefValue(0, new IntType()), type.defaultValue());
    }

    // Test: verifies nested reference types compare recursively.
    @Test
    void nestedRefTypesAreEqualWhenInnerTypesAreEqual() {
        RefType first = new RefType(new RefType(new IntType()));
        RefType second = new RefType(new RefType(new IntType()));

        assertEquals(first, second);
    }

    // Test: verifies nested reference types differ from shallower types.
    @Test
    void nestedRefTypeDiffersFromShallowRefType() {
        RefType nested = new RefType(new RefType(new IntType()));
        RefType shallow = new RefType(new IntType());

        assertNotEquals(nested, shallow);
    }

    // Test: verifies the reference type string representation.
    @Test
    void toStringReturnsNestedRefFormat() {
        RefType type = new RefType(new RefType(new IntType()));

        assertEquals("Ref(Ref(int))", type.toString());
    }
}
