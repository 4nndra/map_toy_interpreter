package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Tests allocation and access behavior of the heap.
class MyHeapTest {

    // Test: verifies that allocated addresses start at one and increase.
    @Test
    void allocateReturnsIncreasingPositiveAddresses() {
        MyHeap heap = new MyHeap();

        assertEquals(1, heap.allocate(new IntValue(10)));
        assertEquals(2, heap.allocate(new IntValue(20)));
        assertEquals(3, heap.allocate(new IntValue(30)));
    }

    // Test: verifies lookup and defined checks for allocated addresses.
    @Test
    void getReturnsAllocatedValue() throws ADTException {
        MyHeap heap = new MyHeap();
        int address = heap.allocate(new IntValue(10));

        assertTrue(heap.isDefined(address));
        assertEquals(new IntValue(10), heap.get(address));
    }

    // Test: verifies lookup of a missing address throws an ADTException.
    @Test
    void getMissingAddressThrows() {
        MyHeap heap = new MyHeap();

        assertThrows(ADTException.class, () -> heap.get(1));
    }

    // Test: verifies update changes an allocated value.
    @Test
    void updateChangesAllocatedValue() throws ADTException {
        MyHeap heap = new MyHeap();
        int address = heap.allocate(new IntValue(10));

        heap.update(address, new IntValue(20));

        assertEquals(new IntValue(20), heap.get(address));
    }

    // Test: verifies update of a missing address throws an ADTException.
    @Test
    void updateMissingAddressThrows() {
        MyHeap heap = new MyHeap();

        assertThrows(ADTException.class, () -> heap.update(1, new IntValue(20)));
    }

    // Test: verifies setContent does not reset the address counter.
    @Test
    void setContentDoesNotReuseAllocatedAddresses() {
        MyHeap heap = new MyHeap();
        heap.allocate(new IntValue(10));
        heap.setContent(java.util.Map.of());

        assertFalse(heap.isDefined(1));
        assertEquals(2, heap.allocate(new IntValue(20)));
    }
}
