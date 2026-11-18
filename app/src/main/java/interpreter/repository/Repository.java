package interpreter.repository;

import interpreter.exceptions.RepositoryException;
import interpreter.model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Stores program states and persists each execution step to a log file.
public class Repository implements IRepository {
    private List<PrgState> programs;
    private final String logFilePath;

    public Repository(PrgState program, String logFilePath) {
        this.programs = new ArrayList<>();
        this.programs.add(program);
        this.logFilePath = logFilePath;
    }

    @Override
    // Returns the currently executing program state.
    public PrgState getCrtPrg() {
        if (programs.isEmpty()) {
            return null;
        }
        return programs.get(0);
    }

    @Override
    // Returns all program states managed by the repository.
    public List<PrgState> getPrgList() {
        return programs;
    }

    @Override
    // Replaces the repository program-state list.
    public void setPrgList(List<PrgState> states) {
        this.programs = states;
    }

    @Override
    // Writes the current program state to the repository log file.
    public void logPrgStateExec() throws RepositoryException {
        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(logFilePath, true))) {

            writer.write(getCrtPrg().toLogString());
            writer.write("\n");

        } catch (IOException e) {
            throw new RepositoryException(
                    "Could not write to log file: " + e.getMessage()
            );
        }
    }
}
