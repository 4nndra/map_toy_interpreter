package interpreter.model.exp;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IHeap;
import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

// Reads the value stored at a reference's heap address.
public class ReadHeapExp implements IExp {
    private final IExp exp;

    // Creates a heap-read expression from an address expression.
    public ReadHeapExp(IExp exp) {
        this.exp = exp;
    }

    // Evaluates the reference expression and returns its heap value.
    @Override
    public IValue eval(IDictionary<String, IValue> symTable, IHeap heap) throws MyException {
        IValue value = exp.eval(symTable, heap);
        if (!(value instanceof RefValue reference)) {
            throw new ExpressionException("rH: " + exp + " is not a reference");
        }

        try {
            return heap.get(reference.getAddress());
        } catch (ADTException exception) {
            throw new ExpressionException(
                    "rH: address " + reference.getAddress() + " is not in the heap"
            );
        }
    }

    @Override
    public String toString() {
        return "rH(" + exp + ")";
    }
}
