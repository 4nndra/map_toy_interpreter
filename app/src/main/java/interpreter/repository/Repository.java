package interpreter.repository;

import interpreter.model.PrgState;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<PrgState> programs;

    public Repository(PrgState program) {
        this.programs = new ArrayList<>();
        this.programs.add(program);
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
}
