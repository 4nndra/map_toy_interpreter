package interpreter.model.type;

import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

// Represents a reference to a value of another type.
public class RefType implements IType {
    private final IType inner;

    // Creates a reference type for the supplied location type.
    public RefType(IType inner) {
        this.inner = inner;
    }

    // Returns the type stored at the heap location.
    public IType getInner() {
        return inner;
    }

    // Returns the null reference for this location type.
    @Override
    public IValue defaultValue() {
        return new RefValue(0, inner);
    }

    @Override
    public String toString() {
        return "Ref(" + inner + ")";
    }

    // Compares nested reference types recursively by their inner types.
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RefType other)) {
            return false;
        }
        return inner.equals(other.inner);
    }

    @Override
    public int hashCode() {
        return inner.hashCode();
    }
}
