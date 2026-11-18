package interpreter.repository;

import interpreter.exceptions.RepositoryException;
import interpreter.model.PrgState;

import java.util.List;

// Repository abstraction for accessing states and writing execution logs.
public interface IRepository {
    // Returns the currently executing program state.
    PrgState getCrtPrg();

    // Returns all program states managed by the repository.
    List<PrgState> getPrgList();

    // Replaces the repository program-state list.
    void setPrgList(List<PrgState> states);

    // Writes the current program state to the repository log file.
    void logPrgStateExec() throws RepositoryException;
}