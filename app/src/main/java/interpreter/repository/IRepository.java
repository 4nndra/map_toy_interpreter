package interpreter.repository;

import interpreter.model.PrgState;

public interface IRepository {
    PrgState getProgram();
    void setProgram(PrgState state);
}
