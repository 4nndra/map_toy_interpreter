package interpreter.model.value;

import interpreter.model.type.IType;
import interpreter.model.type.StringType;

public class StringValue implements IValue {

    private final String value;

    public StringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof StringValue other)) {
            return false;
        }

        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
