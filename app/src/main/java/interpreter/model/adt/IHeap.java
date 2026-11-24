package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.model.value.IValue;

import java.util.Map;

// Defines the operations supported by the interpreter's heap.
public interface IHeap {
    // Allocates a value and returns its new address.
    int allocate(IValue value);

    // Returns the value stored at an address.
    IValue get(int address) throws ADTException;

    // Updates the value stored at an existing address.
    void update(int address, IValue value) throws ADTException;

    // Checks whether an address exists in the heap.
    boolean isDefined(int address);

    // Returns an immutable snapshot of heap contents.
    Map<Integer, IValue> getContent();

    // Replaces heap contents, as needed by garbage collection.
    void setContent(Map<Integer, IValue> content);
}
