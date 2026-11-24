package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.exp.ReadHeapExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.stmt.CompStmt;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.NewStmt;
import interpreter.model.stmt.PrintStmt;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.type.RefType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import interpreter.model.type.IntType;
import interpreter.repository.Repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Tests direct and transitive heap reachability during garbage collection.
class GarbageCollectorTest {
    @TempDir
    Path tempDir;

    // Test: finds reference addresses directly present in symbol-table values.
    @Test
    void extractsDirectRootsFromSymbolTableValues() {
        List<IValue> values = List.of(
                new IntValue(7),
                new RefValue(3, new IntType()),
                new RefValue(5, new RefType(new IntType()))
        );

        assertEquals(
                Set.of(3, 5),
                GarbageCollector.getAddrFromSymTable(values)
        );
    }

    // Test: confirms the unsafe collector removes indirectly reachable entries.
    @Test
    void unsafeCollectorKeepsOnlyDirectRoots() {
        Map<Integer, IValue> heap = Map.of(
                1, new IntValue(20),
                2, new RefValue(1, new IntType()),
                3, new IntValue(30)
        );

        assertEquals(
                Map.of(2, new RefValue(1, new IntType())),
                GarbageCollector.unsafeGarbageCollector(Set.of(2), heap)
        );
    }

    // Test: traverses references stored at reachable heap locations.
    @Test
    void reachableAddressesIncludeIndirectReferences() {
        Map<Integer, IValue> heap = Map.of(
                1, new IntValue(20),
                2, new RefValue(1, new IntType()),
                3, new IntValue(30),
                4, new IntValue(7)
        );

        assertEquals(
                Set.of(1, 2, 3),
                GarbageCollector.reachableAddresses(Set.of(2, 3), heap)
        );
    }

    // Test: preserves locations reachable through another heap location.
    @Test
    void safeCollectorKeepsTransitiveLocationsAndRemovesGarbage() {
        Map<Integer, IValue> heap = Map.of(
                1, new IntValue(20),
                2, new RefValue(1, new IntType()),
                3, new IntValue(30),
                4, new IntValue(7)
        );

        assertEquals(
                Set.of(1, 2, 3),
                GarbageCollector.safeGarbageCollector(Set.of(2, 3), heap).keySet()
        );
    }

    // Test: handles a reference cycle without repeatedly visiting its locations.
    @Test
    void reachableAddressesTerminateForCycles() {
        Map<Integer, IValue> heap = Map.of(
                1, new RefValue(2, new IntType()),
                2, new RefValue(1, new IntType())
        );

        assertEquals(
                Set.of(1, 2),
                GarbageCollector.reachableAddresses(Set.of(1), heap)
        );
    }

    // Test: verifies safe collection preserves Ex9's nested reference result.
    @Test
    void controllerKeepsIndirectlyReachableValueDuringExecution() throws MyException {
        IStmt program = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt(
                                        "a",
                                        new RefType(new RefType(new IntType()))
                                ),
                                new CompStmt(
                                        new NewStmt("a", new VarExp("v")),
                                        new CompStmt(
                                                new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(
                                                        new ReadHeapExp(
                                                                new ReadHeapExp(new VarExp("a"))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        PrgState state = PrgState.of(program);
        Controller controller = new Controller(new Repository(
                state,
                tempDir.resolve("ex9.log").toString()
        ));

        controller.allStep();

        assertEquals(new IntValue(20), state.getOut().get(0));
        assertEquals(Set.of(1, 2, 3), state.getHeap().getContent().keySet());
    }
}
