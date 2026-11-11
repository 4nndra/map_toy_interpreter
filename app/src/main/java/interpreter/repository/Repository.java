package interpreter.repository;

import interpreter.exceptions.RepositoryException;
import interpreter.model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<PrgState> programs;
    private final String logFilePath;

    public Repository(PrgState program, String logFilePath) {
        this.programs = new ArrayList<>();
        this.programs.add(program);
        this.logFilePath = logFilePath;
    }

    @Override
    public PrgState getCrtPrg() {
        if (programs.isEmpty()) {
            return null;
        }
        return programs.get(0);
    }

    @Override
    public List<PrgState> getPrgList() {
        return programs;
    }

    @Override
    public void setPrgList(List<PrgState> states) {
        this.programs = states;
    }

    @Override
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
