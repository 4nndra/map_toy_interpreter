package interpreter.model.exp;

import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;

public class ArithExp implements IExp {
    private final ArithOp op;
    private final IExp left;
    private final IExp right;

    public ArithExp(ArithOp op, IExp left, IExp right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }

    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        IValue leftValue = left.eval(symTable);
        IValue rightValue = right.eval(symTable);

        if (!(leftValue instanceof IntValue leftInt) ||
                !(rightValue instanceof IntValue rightInt)) {
            throw new MyException("Arithmetic operands must be integers");
        }

        int a = leftInt.getValue();
        int b = rightInt.getValue();

        return switch (op) {
            case ADD -> new IntValue(a + b);
            case SUB -> new IntValue(a - b);
            case MUL -> new IntValue(a * b);
            case DIV -> {
                if (b == 0) {
                    throw new MyException("Division by zero");
                }
                yield new IntValue(a / b);
            }
        };
    }

    @Override
    public String toString() {
        return "(" + left + " " + op + " " + right + ")";
    }
}
