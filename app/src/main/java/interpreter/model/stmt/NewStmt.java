package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

// Allocates a heap location and updates a reference variable to point to it.
public class NewStmt implements IStmt {
    private final String varName;
    private final IExp exp;

    // Creates an allocation statement for a reference variable and initial value.
    public NewStmt(String varName, IExp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    // Validates the reference and value types before allocating the heap location.
    @Override
    public PrgState execute(PrgState state) throws MyException {
        if (!state.getSymTable().isDefined(varName)) {
            throw new StatementException("new: " + varName + " is not declared");
        }

        IValue currentValue = state.getSymTable().lookup(varName);
        if (!(currentValue instanceof RefValue reference)) {
            throw new StatementException("new: " + varName + " is not a reference");
        }

        IValue value = exp.eval(state.getSymTable(), state.getHeap());
        if (!value.getType().equals(reference.getLocationType())) {
            throw new StatementException(
                    "new: " + exp + " has type " + value.getType()
                            + ", expected " + reference.getLocationType()
            );
        }

        int address = state.getHeap().allocate(value);
        state.getSymTable().update(
                varName,
                new RefValue(address, reference.getLocationType())
        );
        return state;
    }

    @Override
    public String toString() {
        return "new(" + varName + ", " + exp + ")";
    }
}
