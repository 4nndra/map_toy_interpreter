package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public class LogicExp implements IExp {
    private final LogicOp op;
    private final IExp e1;
    private final IExp e2;

    public LogicExp(LogicOp op, IExp e1, IExp e2) {
        this.op = op;
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        IValue v1 = e1.eval(symTable);
        IValue v2 = e2.eval(symTable);

        if (!(v1 instanceof BoolValue bool1) ||
                !(v2 instanceof BoolValue bool2)) {
            throw new ExpressionException(
                    "Logical operations require Boolean operands"
            );
        }

        boolean b1 = bool1.getValue();
        boolean b2 = bool2.getValue();

        return switch (op) {
            case AND -> new BoolValue(b1 && b2);
            case OR -> new BoolValue(b1 || b2);
        };
    }

    @Override
    public String toString() {
        return e1 + " " + op + " " + e2;
    }
}
