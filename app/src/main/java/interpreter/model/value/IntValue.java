package interpreter.model.value;

import interpreter.model.type.IType;
import interpreter.model.type.IntType;

public class IntValue implements IValue {
    private final int value;

    public IntValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IntValue other)) {
            return false;
        }
        return value == other.value;
    }
}
