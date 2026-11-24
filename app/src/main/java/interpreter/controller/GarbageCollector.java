package interpreter.controller;

import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// Collects heap locations that cannot be reached from program variables.
public final class GarbageCollector {
    private GarbageCollector() {
    }

    // Finds heap addresses referenced directly by values in the symbol table.
    public static Set<Integer> getAddrFromSymTable(Collection<IValue> symTableValues) {
        Set<Integer> addresses = new HashSet<>();
        for (IValue value : symTableValues) {
            if (value instanceof RefValue reference) {
                addresses.add(reference.getAddress());
            }
        }
        return addresses;
    }

    // Keeps only locations referenced directly from the symbol table.
    public static Map<Integer, IValue> unsafeGarbageCollector(
            Set<Integer> symTableAddr,
            Map<Integer, IValue> heap
    ) {
        return heap.entrySet().stream()
                .filter(entry -> symTableAddr.contains(entry.getKey()))
                .collect(java.util.stream.Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }

    // Finds all allocated locations reachable by following references in the heap.
    public static Set<Integer> reachableAddresses(
            Set<Integer> roots,
            Map<Integer, IValue> heap
    ) {
        Set<Integer> reachable = new HashSet<>();
        ArrayDeque<Integer> pending = new ArrayDeque<>(roots);

        while (!pending.isEmpty()) {
            int address = pending.removeFirst();
            if (!reachable.add(address)) {
                continue;
            }

            IValue value = heap.get(address);
            if (value instanceof RefValue reference) {
                pending.addLast(reference.getAddress());
            }
        }

        return reachable;
    }

    // Keeps every heap entry reachable from symbol-table references.
    public static Map<Integer, IValue> safeGarbageCollector(
            Set<Integer> symTableAddr,
            Map<Integer, IValue> heap
    ) {
        Set<Integer> reachable = reachableAddresses(symTableAddr, heap);
        return heap.entrySet().stream()
                .filter(entry -> reachable.contains(entry.getKey()))
                .collect(java.util.stream.Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }
}
