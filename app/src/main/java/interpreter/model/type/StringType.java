package interpreter.model.type;

import interpreter.model.value.IValue;
import interpreter.model.value.StringValue;

// Represents the language's string type and its default value.
public class StringType implements IType {

    @Override
    // Returns the type-specific default value.
    public IValue defaultValue() {
        return new StringValue("");
    }

    @Override
    // Returns a readable representation of this object.
    public String toString() {
        return "string";
    }

    @Override
    // Compares this object with another value for semantic equality.
    public boolean equals(Object obj) {
        return obj instanceof StringType;
    }

    @Override
    // Returns a hash consistent with semantic equality.
    public int hashCode() {
        return StringType.class.hashCode();
    }
}
