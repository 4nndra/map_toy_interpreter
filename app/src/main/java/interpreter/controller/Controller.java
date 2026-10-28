
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

        // Display the initial program state.
        System.out.println("Initial state:");
        System.out.println(state);

        // Execute one statement at a time.
        while (!state.getExeStack().isEmpty()) {
            oneStep();

            // Display the state after each step.
            System.out.println("\nState after step:");
            System.out.println(state);
        }

        return state;
    }
}
