package repository;

import model.PrgState;
import model.exceptions.CustomException;
import model.statement.IStmt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Repository implements IRepo{
    private List<PrgState> states;
    private IStmt originalProgram;
    private String fileName;

    public Repository(PrgState prgState, String fileName) throws IOException, CustomException {
        this.originalProgram = prgState.getOriginalProgram();
        this.fileName = fileName;
        File file = new File(fileName);
        file.createNewFile();
        try (FileWriter fileWriter = new FileWriter(file)){
            fileWriter.write("");
        }
        catch (IOException ex){
            throw new CustomException(ex.getMessage());
        }
        states = new LinkedList<>();
        states.add(prgState);
    }

    public Repository() {
        states = new LinkedList<>();
    }

    public Repository(String givenFile) {
        this.fileName = givenFile;
        states = new LinkedList<>();
    }


    @Override
    public List<PrgState> getPrgList() {
        return states;
    }

    @Override
    public PrgState getCrtPrg() {
        PrgState state = states.get(0);
        states.remove(0);
        return state;
    }

    @Override
    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    @Override
    public void printPrgState(PrgState prgState) throws CustomException, IOException {
        File file = new File(fileName);
        file.createNewFile();
        try (FileWriter fileWriter = new FileWriter(file, true)){
            fileWriter.write(prgState + "\n");
            fileWriter.close();
        }
        catch (IOException ex){
            throw new CustomException(ex.getMessage());
        }

        //System.out.println(prgState + "\n");
    }

    @Override
    public void addState(PrgState state) {
        states.add(state);
    }
}
