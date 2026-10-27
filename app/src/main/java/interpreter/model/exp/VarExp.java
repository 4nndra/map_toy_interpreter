package interpreter.model.exp;

import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;

public class VarExp implements IExp {
    private final String id;

    public VarExp(String id) {
        this.id = id;
    }

    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        return symTable.lookup(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
