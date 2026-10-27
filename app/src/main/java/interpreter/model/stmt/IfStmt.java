package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public class IfStmt implements IStmt {
    private final IExp condition;
    private final IStmt thenStmt;
    private final IStmt elseStmt;

    public IfStmt(IExp condition, IStmt thenStmt, IStmt elseStmt) {
        this.condition = condition;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue value = condition.eval(state.getSymTable());

        if (!(value instanceof BoolValue boolValue)) {
            throw new MyException("If condition must be boolean");
        }

        if (boolValue.getValue()) {
            state.getExeStack().push(thenStmt);
        } else {
            state.getExeStack().push(elseStmt);
        }

        return state;
    }

    @Override
    public String toString() {
        return "if (" + condition + ") then (" + thenStmt + ") else (" + elseStmt + ")";
    }
}
