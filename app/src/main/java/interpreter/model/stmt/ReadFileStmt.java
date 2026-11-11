package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt {
    private final IExp exp;
    private final String varName;

    public ReadFileStmt(IExp exp, String varName) {
        this.exp = exp;
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if (!state.getSymTable().isDefined(varName)) {
            throw new StatementException(
                    "Variable '" + varName + "' is not declared"
            );
        }

        if (!(state.getSymTable().lookup(varName) instanceof IntValue)) {
            throw new StatementException(
                    "Variable '" + varName + "' must be an integer"
            );
        }

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
            String line = reader.readLine();
            int number = (line == null) ? 0 : Integer.parseInt(line.trim());

            state.getSymTable().update(varName, new IntValue(number));
        } catch (IOException | NumberFormatException e) {
            throw new StatementException(
                    "Could not read an integer from file: " + fileName.getValue()
            );
        }

        return state;
    }

    @Override
    public String toString() {
        return "readFile(" + exp + ", " + varName + ")";
    }
}
