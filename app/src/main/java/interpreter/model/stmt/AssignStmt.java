package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.IDictionary;
import interpreter.model.exp.IExp;
import interpreter.model.type.IType;
import interpreter.model.value.IValue;

public class AssignStmt implements IStmt {
    private final String name;
    private final IExp expression;

    public AssignStmt(String name, IExp expression) {
        this.name = name;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDictionary<String, IValue> symTable = state.getSymTable();

        if (!symTable.isDefined(name)) {
            throw new MyException("Variable not declared: " + name);
        }

        IValue value = expression.eval(symTable);
        IType declaredType = symTable.lookup(name).getType();

        if (!declaredType.equals(value.getType())) {
            throw new MyException("Type mismatch assigning to " + name);
        }

        symTable.update(name, value);
        return state;
    }

    @Override
    public String toString() {
        return name + " = " + expression;
    }
}
