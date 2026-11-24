package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.model.value.IValue;

import java.util.HashMap;
import java.util.Map;

// Heap implementation that allocates unique positive addresses.
public class MyHeap implements IHeap {
    private final Map<Integer, IValue> heap = new HashMap<>();
    private int nextFree = 1;

    // Stores a value at the next unused address.
    @Override
    public int allocate(IValue value) {
        int address = nextFree++;
        heap.put(address, value);
        return address;
    }

    // Returns the value at an address or reports an invalid address.
    @Override
    public IValue get(int address) throws ADTException {
        if (!heap.containsKey(address)) {
            throw new ADTException("address is not in the heap: " + address);
        }
        return heap.get(address);
    }

    // Updates an existing heap location.
    @Override
    public void update(int address, IValue value) throws ADTException {
        if (!heap.containsKey(address)) {
            throw new ADTException("address is not in the heap: " + address);
        }
        heap.put(address, value);
    }

    // Checks whether an address is currently allocated.
    @Override
    public boolean isDefined(int address) {
        return heap.containsKey(address);
    }

    // Returns a read-only copy of the heap contents.
    @Override
    public Map<Integer, IValue> getContent() {
        return Map.copyOf(heap);
    }

    // Replaces heap entries without reusing previously allocated addresses.
    @Override
    public void setContent(Map<Integer, IValue> content) {
        heap.clear();
        heap.putAll(content);
    }

    @Override
    public String toString() {
        return heap.toString();
    }
}
