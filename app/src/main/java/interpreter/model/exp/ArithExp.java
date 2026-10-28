package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;

public class ArithExp implements IExp {
    private final ArithOp op;
    private final IExp e1;
    private final IExp e2;

    public ArithExp(ArithOp op, IExp e1, IExp e2) {
        this.op = op;
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        IValue v1 = e1.eval(symTable);
        IValue v2 = e2.eval(symTable);

        if (!(v1 instanceof IntValue int1) || !(v2 instanceof IntValue int2)) {
            throw new ExpressionException(
                    "Arithmetic operations require integer operands"
            );
        }

        int n1 = int1.getValue();
        int n2 = int2.getValue();

        return switch (op) {
            case ADD -> new IntValue(n1 + n2);
            case SUB -> new IntValue(n1 - n2);
            case MUL -> new IntValue(n1 * n2);
            case DIV -> {
                if (n2 == 0) {
                    throw new ExpressionException("Division by zero");
                }
                yield new IntValue(n1 / n2);
            }
        };
    }

    @Override
    public String toString() {
        return e1 + " " + op + " " + e2;
    }
}
