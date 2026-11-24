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

    // Pops and executes exactly one statement from the execution stack.
    public PrgState oneStep(PrgState state) throws MyException {
        if (state == null || state.getExeStack().isEmpty()) {
            throw new StatementException("Program execution stack is empty");
        }

        IStmt currentStatement = state.getExeStack().pop();
        return currentStatement.execute(state);
    }

    // Logs the complete execution: the initial state and every state after one step.
    public void allStep() throws MyException {
        PrgState state = repository.getCrtPrg();

        if (state == null) {
            throw new StatementException("No program state available");
        }

        repository.logPrgStateExec();

        System.out.println("Initial state:");
        System.out.println(state);

        while (!state.getExeStack().isEmpty()) {
            // Pops and executes exactly one statement from the execution stack.
            oneStep(state);

            // Log the just-executed state before removing unreachable heap entries.
            repository.logPrgStateExec();
            state.getHeap().setContent(GarbageCollector.safeGarbageCollector(
                    GarbageCollector.getAddrFromSymTable(
                            state.getSymTable().toMap().values()
                    ),
                    state.getHeap().getContent()
            ));
            // Log again so the effects of garbage collection are visible.
            repository.logPrgStateExec();

            System.out.println("\nState after step:");
            System.out.println(state);
        }
    }
}
