package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

// Closes an open file and removes it from the program's FileTable.
public class CloseRFileStmt implements IStmt {
    private final IExp exp;

    public CloseRFileStmt(IExp exp) {
        this.exp = exp;
    }

    @Override
    // Executes this statement and returns the updated program state.
    public PrgState execute(PrgState state) throws MyException {
        IValue value = exp.eval(state.getSymTable(), state.getHeap());

        if (!(value instanceof StringValue)) {
            throw new StatementException(
                    "File name expression must evaluate to a string"
            );
        }

        StringValue fileName = (StringValue) value;

        if (!state.getFileTable().isDefined(fileName)) {
            throw new StatementException(
                    "File is not open: " + fileName.getValue()
            );
        }

        BufferedReader reader = state.getFileTable().lookup(fileName);

        try {
            reader.close();
            state.getFileTable().remove(fileName);
        } catch (IOException e) {
            throw new StatementException(
                    "Could not close file: " + fileName.getValue()
            );
        }

        return state;
    }

    @Override
    // Returns a readable representation of this object.
    public String toString() {
        return "closeRFile(" + exp + ")";
    }
}
