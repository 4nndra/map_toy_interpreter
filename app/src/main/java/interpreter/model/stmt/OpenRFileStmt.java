package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStmt implements IStmt {
    private final IExp exp;

    public OpenRFileStmt(IExp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue value = exp.eval(state.getSymTable());

        if (!(value instanceof StringValue)) {
            throw new StatementException(
                    "The file name expression must evaluate to a string"
            );
        }

        StringValue fileName = (StringValue) value;

        if (state.getFileTable().isDefined(fileName)) {
            throw new StatementException(
                    "File is already open: " + fileName.getValue()
            );
        }

        try {
            BufferedReader reader =
                    new BufferedReader(new FileReader(fileName.getValue()));

            state.getFileTable().put(fileName, reader);
        } catch (IOException e) {
            throw new StatementException(
                    "Could not open file: " + fileName.getValue()
            );
        }

        return state;
    }

    @Override
    public String toString() {
        return "openRFile(" + exp + ")";
    }
}
