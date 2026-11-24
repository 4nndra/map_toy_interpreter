package interpreter.model.stmt;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ArithExp;
import interpreter.model.exp.RelationalExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.exp.ArithOp;
import interpreter.model.exp.RelOp;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// Tests while-loop execution counts and invalid conditions.
class WhileStmtTest {
    @TempDir
    Path tempDir;

    // Runs a loop that counts down from its initial value and records each value.
    private List<IntValue> runCountdown(int initialValue) throws MyException {
        IStmt program = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(initialValue))),
                        new WhileStmt(
                                new RelationalExp(
                                        RelOp.GT,
                                        new VarExp("v"),
                                        new ValueExp(new IntValue(0))
                                ),
                                new CompStmt(
                                        new PrintStmt(new VarExp("v")),
                                        new AssignStmt(
                                                "v",
                                                new ArithExp(
                                                        ArithOp.SUB,
                                                        new VarExp("v"),
                                                        new ValueExp(new IntValue(1))
                                                )
                                        )
                                )
                        )
                )
        );
        PrgState state = PrgState.of(program);
        Controller controller = new Controller(
                new Repository(state, tempDir.resolve("while-" + initialValue + ".log").toString())
        );

        while (!state.getExeStack().isEmpty()) {
            controller.oneStep(state);
        }

        List<IntValue> output = new ArrayList<>();
        for (int i = 0; i < state.getOut().size(); i++) {
            output.add((IntValue) state.getOut().get(i));
        }
        assertEquals(new IntValue(0), state.getSymTable().lookup("v"));
        return output;
    }

    // Test: verifies a false condition skips the loop body.
    @Test
    void loopWithZeroIterations() throws MyException {
        assertEquals(List.of(), runCountdown(0));
    }

    // Test: verifies the loop executes exactly once when initially one.
    @Test
    void loopWithOneIteration() throws MyException {
        assertEquals(List.of(new IntValue(1)), runCountdown(1));
    }

    // Test: verifies repeated iterations re-evaluate the condition.
    @Test
    void loopWithSeveralIterations() throws MyException {
        assertEquals(
                List.of(new IntValue(3), new IntValue(2), new IntValue(1)),
                runCountdown(3)
        );
    }

    // Test: verifies a non-boolean condition is rejected.
    @Test
    void nonBooleanConditionThrows() {
        PrgState state = PrgState.of(new NopStmt());
        WhileStmt loop = new WhileStmt(
                new ValueExp(new IntValue(1)),
                new NopStmt()
        );

        assertThrows(StatementException.class, () -> loop.execute(state));
    }

    // Test: verifies the textual representation includes condition and body.
    @Test
    void toStringShowsConditionAndBody() {
        WhileStmt loop = new WhileStmt(
                new ValueExp(new BoolValue(true)),
                new NopStmt()
        );

        assertEquals("(while (true) nop)", loop.toString());
    }
}
