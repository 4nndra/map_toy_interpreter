package interpreter.model.value;

import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

// Tests reference values, their runtime types and value equality.
class RefValueTest {

    // Test: verifies a reference value reports its reference type.
    @Test
    void getTypeReturnsCorrectRefType() {
        RefValue value = new RefValue(1, new IntType());

        assertEquals(new RefType(new IntType()), value.getType());
    }

    // Test: verifies equal reference values have equal fields and hash codes.
    @Test
    void equalReferenceValuesAreEqual() {
        RefValue first = new RefValue(1, new IntType());
        RefValue second = new RefValue(1, new IntType());

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    // Test: verifies address differences make reference values unequal.
    @Test
    void differentAddressesAreUnequal() {
        assertNotEquals(
                new RefValue(1, new IntType()),
                new RefValue(2, new IntType())
        );
    }

    // Test: verifies nested reference values format correctly.
    @Test
    void toStringIncludesAddressAndLocationType() {
        RefValue value = new RefValue(
                10,
                new RefType(new IntType())
        );

        assertEquals("(10, Ref(int))", value.toString());
    }
}
