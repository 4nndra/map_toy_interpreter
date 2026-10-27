package interpreter.repository;

import interpreter.model.PrgState;

public class Repository implements IRepository {
    private PrgState program;

    public Repository(PrgState program) {
        this.program = program;
    }

    @Override
    public PrgState getProgram() {
        return program;
    }

    @Override
    public void setProgram(PrgState state) {
        this.program = state;
    }
}
