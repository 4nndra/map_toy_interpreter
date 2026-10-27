package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.value.IValue;

public class PrintStmt implements IStmt {
    private final interpreter.model.exp.IExp expression;

    public PrintStmt(interpreter.model.exp.IExp expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue value = expression.eval(state.getSymTable());
        state.getOut().add(value);
        return state;
    }

    @Override
    public String toString() {
        return "print(" + expression + ")";
    }
}
