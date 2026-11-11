package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStmt implements IStmt {
    private final IExp exp;

    public CloseRFileStmt(IExp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue value = exp.eval(state.getSymTable());

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
    public String toString() {
        return "closeRFile(" + exp + ")";
    }
}
