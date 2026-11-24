package interpreter.model.exp;

import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IHeap;
import interpreter.model.value.IValue;

public interface IExp {
    // Evaluates an expression using the variables and heap of the current state.
    IValue eval(IDictionary<String, IValue> symTable, IHeap heap) throws MyException;
}
