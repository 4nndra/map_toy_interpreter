package interpreter.model.value;

import interpreter.model.type.IType;
import interpreter.model.type.RefType;

// Represents a heap address together with the type of the value stored there.
public class RefValue implements IValue {
    private final int address;
    private final IType locationType;

    // Creates a reference value for an address and location type.
    public RefValue(int address, IType locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    // Returns the heap address held by this reference.
    public int getAddress() {
        return address;
    }

    // Returns the type of the value stored at the referenced location.
    public IType getLocationType() {
        return locationType;
    }

    // Returns the reference type of this value.
    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    @Override
    public String toString() {
        return "(" + address + ", " + locationType + ")";
    }

    // Compares references by address and location type.
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RefValue other)) {
            return false;
        }
        return address == other.address && locationType.equals(other.locationType);
    }

    @Override
    public int hashCode() {
        return 31 * Integer.hashCode(address) + locationType.hashCode();
    }
}
