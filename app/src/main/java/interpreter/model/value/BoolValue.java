package interpreter.model.value;

import interpreter.model.type.BoolType;
import interpreter.model.type.IType;

public class BoolValue implements IValue {
    private final boolean value;

    public BoolValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public IType getType() {
        return new BoolType();
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BoolValue other)) {
            return false;
        }
        return value == other.value;
    }
}
