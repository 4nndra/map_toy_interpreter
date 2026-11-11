package interpreter.model.value;

import interpreter.model.type.StringType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringValueTest {

    @Test
    void stringTypeHasEmptyDefaultValue() {
        StringType type = new StringType();

        assertEquals(new StringValue(""), type.defaultValue());
    }

    @Test
    void equalStringValuesAreEqual() {
        StringValue first = new StringValue("hello");
        StringValue second = new StringValue("hello");

        assertEquals(first, second);
    }

    @Test
    void equalStringValuesHaveSameHashCode() {
        StringValue first = new StringValue("hello");
        StringValue second = new StringValue("hello");

        assertEquals(first.hashCode(), second.hashCode());
    }
}