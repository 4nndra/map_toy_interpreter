package interpreter.model.exp;

import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public class LogicExp implements IExp {
    private final LogicOp op;
    private final IExp left;
    private final IExp right;

    public LogicExp(LogicOp op, IExp left, IExp right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }

    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        IValue leftValue = left.eval(symTable);
        IValue rightValue = right.eval(symTable);

        if (!(leftValue instanceof BoolValue leftBool) ||
                !(rightValue instanceof BoolValue rightBool)) {
            throw new MyException("Logical operands must be booleans");
        }

        boolean a = leftBool.getValue();
        boolean b = rightBool.getValue();

        return switch (op) {
            case AND -> new BoolValue(a && b);
            case OR -> new BoolValue(a || b);
        };
    }

    @Override
    public String toString() {
        return "(" + left + " " + op + " " + right + ")";
    }
}
