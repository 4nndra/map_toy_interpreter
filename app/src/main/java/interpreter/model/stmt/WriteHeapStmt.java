package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

// Writes a type-compatible value into a referenced heap location.
public class WriteHeapStmt implements IStmt {
    private final String varName;
    private final IExp exp;

    // Creates a heap-write statement for a reference variable and value expression.
    public WriteHeapStmt(String varName, IExp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    // Checks the reference, address and value type before updating the heap.
    @Override
    public PrgState execute(PrgState state) throws MyException {
        if (!state.getSymTable().isDefined(varName)) {
            throw new StatementException("wH: " + varName + " is not declared");
        }

        IValue currentValue = state.getSymTable().lookup(varName);
        if (!(currentValue instanceof RefValue reference)) {
            throw new StatementException("wH: " + varName + " is not a reference");
        }

        if (!state.getHeap().isDefined(reference.getAddress())) {
            throw new StatementException(
                    "wH: address " + reference.getAddress() + " is not in the heap"
            );
        }

        IValue value = exp.eval(state.getSymTable(), state.getHeap());
        if (!value.getType().equals(reference.getLocationType())) {
            throw new StatementException(
                    "wH: " + exp + " has type " + value.getType()
                            + ", expected " + reference.getLocationType()
            );
        }

        state.getHeap().update(reference.getAddress(), value);
        return state;
    }

    @Override
    public String toString() {
        return "wH(" + varName + ", " + exp + ")";
    }
}
