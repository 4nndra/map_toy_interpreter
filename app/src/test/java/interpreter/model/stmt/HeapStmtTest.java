package interpreter.model.stmt;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ReadHeapExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// Tests allocation, reading and writing through heap references.
class HeapStmtTest {
    private PrgState state;

    // Prepares a state with an integer reference variable for each test.
    @BeforeEach
    void setUp() throws MyException {
        state = PrgState.of(new NopStmt());
        new VarDeclStmt("v", new RefType(new IntType())).execute(state);
    }

    // Test: verifies allocation updates the variable and stores its value.
    @Test
    void newAllocatesValueAndUpdatesReference() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);

        assertEquals(new RefValue(1, new IntType()), state.getSymTable().lookup("v"));
        assertEquals(new IntValue(20), state.getHeap().get(1));
    }

    // Test: verifies allocation rejects an undeclared variable.
    @Test
    void newWithUndeclaredVariableThrows() {
        assertThrows(
                StatementException.class,
                () -> new NewStmt("missing", new ValueExp(new IntValue(20))).execute(state)
        );
    }

    // Test: verifies allocation rejects a variable that is not a reference.
    @Test
    void newWithNonReferenceVariableThrows() throws MyException {
        new VarDeclStmt("x", new IntType()).execute(state);

        assertThrows(
                StatementException.class,
                () -> new NewStmt("x", new ValueExp(new IntValue(20))).execute(state)
        );
    }

    // Test: verifies allocation rejects an initial value of the wrong type.
    @Test
    void newWithWrongValueTypeThrows() {
        assertThrows(
                StatementException.class,
                () -> new NewStmt("v", new ValueExp(new BoolValue(true))).execute(state)
        );
        assertEquals(0, state.getHeap().getContent().size());
    }

    // Test: verifies rH returns the value at a valid reference address.
    @Test
    void readHeapReturnsStoredValue() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);

        assertEquals(
                new IntValue(20),
                new ReadHeapExp(new ValueExp(new RefValue(1, new IntType())))
                        .eval(state.getSymTable(), state.getHeap())
        );
    }

    // Test: verifies rH rejects expressions that do not evaluate to references.
    @Test
    void readHeapWithNonReferenceThrows() {
        assertThrows(
                ExpressionException.class,
                () -> new ReadHeapExp(new ValueExp(new IntValue(1)))
                        .eval(state.getSymTable(), state.getHeap())
        );
    }

    // Test: verifies rH rejects references to missing heap locations.
    @Test
    void readHeapWithMissingAddressThrows() {
        assertThrows(
                ExpressionException.class,
                () -> new ReadHeapExp(new ValueExp(new RefValue(10, new IntType())))
                        .eval(state.getSymTable(), state.getHeap())
        );
    }

    // Test: verifies wH replaces the value at a reference's heap address.
    @Test
    void writeHeapUpdatesStoredValue() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);
        new WriteHeapStmt("v", new ValueExp(new IntValue(30))).execute(state);

        assertEquals(new IntValue(30), state.getHeap().get(1));
    }

    // Test: verifies wH rejects an undeclared variable.
    @Test
    void writeHeapWithUndeclaredVariableThrows() {
        assertThrows(
                StatementException.class,
                () -> new WriteHeapStmt("missing", new ValueExp(new IntValue(30)))
                        .execute(state)
        );
    }

    // Test: verifies wH rejects a variable that is not a reference.
    @Test
    void writeHeapWithNonReferenceVariableThrows() throws MyException {
        new VarDeclStmt("x", new IntType()).execute(state);

        assertThrows(
                StatementException.class,
                () -> new WriteHeapStmt("x", new ValueExp(new IntValue(30))).execute(state)
        );
    }

    // Test: verifies wH rejects a reference to a missing heap location.
    @Test
    void writeHeapWithMissingAddressThrows() throws MyException {
        state.getSymTable().update("v", new RefValue(10, new IntType()));

        assertThrows(
                StatementException.class,
                () -> new WriteHeapStmt("v", new ValueExp(new IntValue(30))).execute(state)
        );
    }

    // Test: verifies wH rejects a value with the wrong location type.
    @Test
    void writeHeapWithWrongValueTypeThrows() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);

        assertThrows(
                StatementException.class,
                () -> new WriteHeapStmt("v", new ValueExp(new BoolValue(true))).execute(state)
        );
        assertEquals(new IntValue(20), state.getHeap().get(1));
    }
}
