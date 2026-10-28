package interpreter.repository;

import interpreter.model.PrgState;
import java.util.List;

public interface IRepository {
    PrgState getCrtPrg();
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> states);
}
