package interpreter.model.value;

import interpreter.model.type.IType;
import interpreter.model.type.StringType;

// Represents an immutable string value in a program's symbol table.
public class StringValue implements IValue {

    private final String value;

    public StringValue(String value) {
        this.value = value;
    }

    // Returns the wrapped primitive value.
    public String getValue() {
        return value;
    }

    @Override
    // Returns the runtime type of this value.
    public IType getType() {
        return new StringType();
    }

    @Override
    // Returns a readable representation of this object.
    public String toString() {
        return "\"" + value + "\"";
    }

    @Override
    // Compares this object with another value for semantic equality.
    public boolean equals(Object obj) {
        if (!(obj instanceof StringValue other)) {
            return false;
        }

        return value.equals(other.value);
    }

    @Override
    // Returns a hash consistent with semantic equality.
    public int hashCode() {
        return value.hashCode();
    }
}
