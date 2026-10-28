package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.type.IType;
import interpreter.model.value.IValue;

public class AssignStmt implements IStmt {
    private final String id;
    private final IExp exp;

    public AssignStmt(String id, IExp exp) {
        this.id = id;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if (!state.getSymTable().isDefined(id)) {
            throw new StatementException(
                    "Variable '" + id + "' is not declared"
            );
        }

        IValue value = exp.eval(state.getSymTable());
        IType declaredType = state.getSymTable().lookup(id).getType();

        if (!declaredType.equals(value.getType())) {
            throw new StatementException(
                    "Type mismatch when assigning to variable '" + id + "'"
            );
        }

        state.getSymTable().update(id, value);
        return state;
    }

    @Override
    public String toString() {
        return id + " = " + exp;
    }
}
