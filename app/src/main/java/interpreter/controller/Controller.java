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

    public PrgState oneStep(PrgState state) throws MyException {
        if (state == null || state.getExeStack().isEmpty()) {
            throw new StatementException("Program execution stack is empty");
        }

        IStmt currentStatement = state.getExeStack().pop();
        return currentStatement.execute(state);
    }

    public void allStep() throws MyException {
        PrgState state = repository.getCrtPrg();

        if (state == null) {
            throw new StatementException("No program state available");
        }

        System.out.println("Initial state:");
        System.out.println(state);

        while (!state.getExeStack().isEmpty()) {
            oneStep(state);

            System.out.println("\nState after step:");
            System.out.println(state);
        }
    }
}
