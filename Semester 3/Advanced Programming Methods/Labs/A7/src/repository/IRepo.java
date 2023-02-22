package repository;

import model.PrgState;
import model.exceptions.CustomException;
import model.statement.IStmt;

import java.io.IOException;
import java.util.List;

public interface IRepo {
    public List<PrgState> getPrgList();

    void setPrgList(List<PrgState> list);

    PrgState getCrtPrg();
    IStmt getOriginalProgram();
    void printPrgState(PrgState prgState) throws CustomException, IOException;

    void addState(PrgState state);
}
