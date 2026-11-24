package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

// Repeats its body by scheduling the loop and body on the execution stack.
public class WhileStmt implements IStmt {
    private final IExp cond;
    private final IStmt body;

    // Creates a while statement from a condition and body.
    public WhileStmt(IExp cond, IStmt body) {
        this.cond = cond;
        this.body = body;
    }

    // Pushes the body and loop again only when the condition evaluates to true.
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue condition = cond.eval(state.getSymTable(), state.getHeap());
        if (!(condition instanceof BoolValue boolValue)) {
            throw new StatementException("While condition must be Boolean");
        }

        if (boolValue.getValue()) {
            state.getExeStack().push(this);
            state.getExeStack().push(body);
        }
        return state;
    }

    @Override
    public String toString() {
        return "(while (" + cond + ") " + body + ")";
    }
}
