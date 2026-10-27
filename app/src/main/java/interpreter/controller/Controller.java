package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.repository.IRepository;

public class Controller {
    private final IRepository repository;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public PrgState oneStep() throws MyException {
        PrgState state = repository.getProgram();

        if (state.getExeStack().isEmpty()) {
            throw new MyException("Program execution stack is empty");
        }

        IStmt currentStatement = state.getExeStack().pop();
        currentStatement.execute(state);

        return state;
    }

    public PrgState allStep() throws MyException {
        PrgState state = repository.getProgram();

        while (!state.getExeStack().isEmpty()) {
            oneStep();
        }

        return state;
    }
}
