package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IHeap;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;

// Evaluates two operands and produces a boolean comparison result.
public class RelationalExp implements IExp {
    private final RelOp op;
    private final IExp left;
    private final IExp right;

    public RelationalExp(RelOp op, IExp left, IExp right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }

    @Override
    // Evaluates the expression against the current symbol table.
    public IValue eval(IDictionary<String, IValue> symTable, IHeap heap)
            throws MyException {

        IValue v1 = left.eval(symTable, heap);
        IValue v2 = right.eval(symTable, heap);

        if (!(v1 instanceof IntValue int1)) {
            throw new ExpressionException(
                    "First operand is not an integer"
            );
        }

        if (!(v2 instanceof IntValue int2)) {
            throw new ExpressionException(
                    "Second operand is not an integer"
            );
        }

        int n1 = int1.getValue();
        int n2 = int2.getValue();

        return new BoolValue(switch (op) {
            case LT -> n1 < n2;
            case LE -> n1 <= n2;
            case EQ -> n1 == n2;
            case NE -> n1 != n2;
            case GT -> n1 > n2;
            case GE -> n1 >= n2;
        });
    }

    @Override
    // Returns a readable representation of this object.
    public String toString() {
        return "(" + left + " " + op + " " + right + ")";
    }
}
