package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssignStmtTest {

    private PrgState createState() {
        return new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                new NopStmt()
        );
    }

    @Test
    void testAssignIntegerValue() throws MyException {
        PrgState state = createState();

        new VarDeclStmt("x", new IntType()).execute(state);
        new AssignStmt("x", new ValueExp(new IntValue(10))).execute(state);

        assertEquals(new IntValue(10), state.getSymTable().lookup("x"));
    }

    @Test
    void testAssignToUndeclaredVariable() {
        PrgState state = createState();

        AssignStmt stmt = new AssignStmt("x", new ValueExp(new IntValue(10)));

        assertThrows(
                StatementException.class,
                () -> stmt.execute(state)
        );
    }

    @Test
    void testAssignWrongType() throws MyException {
        PrgState state = createState();

        new VarDeclStmt("x", new IntType()).execute(state);

        AssignStmt stmt = new AssignStmt(
                "x",
                new ValueExp(new interpreter.model.value.BoolValue(true))
        );

        assertThrows(
                StatementException.class,
                () -> stmt.execute(state)
        );
    }
}