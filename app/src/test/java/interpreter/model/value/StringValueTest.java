package interpreter.model.value;

import interpreter.model.type.StringType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringValueTest {

    // Test: verifies string type has empty default value.
    @Test
    void stringTypeHasEmptyDefaultValue() {
        StringType type = new StringType();

        assertEquals(new StringValue(""), type.defaultValue());
    }

    // Test: verifies equal string values are equal.
    @Test
    void equalStringValuesAreEqual() {
        StringValue first = new StringValue("hello");
        StringValue second = new StringValue("hello");

        assertEquals(first, second);
    }

    // Test: verifies equal string values have same hash code.
    @Test
    void equalStringValuesHaveSameHashCode() {
        StringValue first = new StringValue("hello");
        StringValue second = new StringValue("hello");

        assertEquals(first.hashCode(), second.hashCode());
    }
}