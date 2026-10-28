package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
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
            throw new StatementException("Program execution stack is empty");
        }

        IStmt currentStatement = state.getExeStack().pop();
        currentStatement.execute(state);

        return state;
    }

    public PrgState allStep() throws MyException {
        PrgState state = repository.getProgram();

        System.out.println("Initial state:");
        System.out.println(state);

        while (!state.getExeStack().isEmpty()) {
            oneStep();

            System.out.println("\nState after step:");
            System.out.println(state);
        }

        return state;
    }
}
