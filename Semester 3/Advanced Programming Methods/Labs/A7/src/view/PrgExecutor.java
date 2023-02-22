package view;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.ADT.*;
import model.DataTransfer.HeapEntry;
import model.DataTransfer.SymTableEntry;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.CustomException;
import model.statement.IStmt;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PrgExecutor {
    private Controller controller;
    private PrgState lastMainProgram;

    @FXML
    private Label prgStateCounter;

    @FXML
    private Button oneStepButton;

    @FXML
    private ListView<Integer> prgStateListView;

    @FXML
    private TableView<HeapEntry> heapTableView;

    @FXML
    private ListView<Value> outListView;

    @FXML
    private ListView<IStmt> exeStackListView;

    @FXML
    private TableView<SymTableEntry> symTableTableView;

    @FXML
    private ListView<StringValue> fileTableListView;

    @FXML
    private TableColumn<HeapEntry, String> heapAddress;

    @FXML
    private TableColumn<HeapEntry, String> heapValue;

    @FXML
    private TableColumn<SymTableEntry, String> symTableName;

    @FXML
    private TableColumn<SymTableEntry, String> symTableValue;

    public PrgExecutor(Controller givenController) {
        controller = givenController;
        this.lastMainProgram = null;
    }

    @FXML
    void initialize() {
        heapAddress.setCellValueFactory(new PropertyValueFactory<HeapEntry, String>("Address"));
        heapValue.setCellValueFactory(new PropertyValueFactory<HeapEntry, String>("Value"));
        symTableName.setCellValueFactory(new PropertyValueFactory<SymTableEntry, String>("Name"));
        symTableValue.setCellValueFactory(new PropertyValueFactory<SymTableEntry, String>("Value"));
        prgStateListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        try {
            populateWidgets();
        }
        catch (CustomException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nothing to execute");
            alert.setHeaderText("There is nothing left to execute!");

            alert.showAndWait();
        }
    }


    @FXML
    private void executeOneStep() throws InterruptedException, CustomException {
        try {
            List<PrgState> states = controller.getRepository().getPrgList();
            if (states.size() > 0)
                controller.executeOneStep();
        }
        catch (CustomException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Execution Error");
            alert.setHeaderText("An execution error has occurred!");
            alert.setContentText(e.toString());

            alert.showAndWait();

        }

        populateWidgets();
    }

    private void populateWidgets() throws CustomException {
        List<PrgState> states = controller.getRepository().getPrgList();
        PrgState currentProgram;
        if (states.size() == 0) {
            prgStateCounter.setText("Program states: 0");
            if (lastMainProgram == null) {
                throw new CustomException("Empty program");
            }
            else {
                currentProgram = lastMainProgram;
                lastMainProgram = null;
            }
        }
        else {
            lastMainProgram = states.get(0);
            currentProgram = states.get(0);
            prgStateCounter.setText("Program states: " + states.size());
        }
        populatePrgStateListView(states);
        prgStateListView.getSelectionModel().selectIndices(0);
        try {
            populateExeStackListView(currentProgram);
            populateHeapTableView(currentProgram);
            populateSymTableTableView(currentProgram);
            populateOutListView(currentProgram);
            populateFileTableListView(currentProgram);
        }
        catch (ADTException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Populating Error");
            alert.setHeaderText("A populating error has occurred!");
            alert.setContentText(e.toString());

            alert.showAndWait();
        }
    }

    private void populateFileTableListView(PrgState prgState) {
        ICustomMap<StringValue, BufferedReader> fileTable = prgState.getFileTable();
        List<StringValue> list = new ArrayList<>(fileTable.getContent().keySet());
        fileTableListView.setItems(FXCollections.observableArrayList(list));
    }

    private void populateOutListView(PrgState prgState) throws ADTException {
        ICustomList<Value> out = prgState.getOutConsole();
        ArrayList<Value> outList = new ArrayList<Value>();
        for (int i = 0; i < out.size(); ++i) {
            outList.add(out.get(i));
        }
        outListView.setItems(FXCollections.observableArrayList(outList));
    }

    private void populateSymTableTableView(PrgState prgState) {
        ICustomMap<String, Value> symTable = prgState.getSymTable();
        ArrayList<SymTableEntry> entries = new ArrayList<SymTableEntry>();
        for (Map.Entry<String, Value> entry: symTable.getContent().entrySet()) {
            entries.add(new SymTableEntry(entry.getKey(), entry.getValue()));
        }
        symTableTableView.setItems(FXCollections.observableArrayList(entries));
    }

    private void populateHeapTableView(PrgState prgState) {
        ICustomHeap<Value> heap = prgState.getHeap();
        ArrayList<HeapEntry> entries = new ArrayList<HeapEntry>();
        for (Map.Entry<Integer, Value> entry: heap.getContent().entrySet()) {
            entries.add(new HeapEntry(entry.getKey(), entry.getValue()));
        }
        heapTableView.setItems(FXCollections.observableArrayList(entries));
    }

    private void populateExeStackListView(PrgState prgState) {
        ICustomStack<IStmt> stk = prgState.getExeStack();
        exeStackListView.setItems(FXCollections.observableArrayList(stk.toList()));
    }

    private void populatePrgStateListView(List<PrgState> states) {
        List<Integer> stateIDs = states.stream().map(PrgState::getStateID).collect(Collectors.toList());
        prgStateListView.setItems(FXCollections.observableArrayList(stateIDs));
    }

    @FXML
    void switchProgramState(MouseEvent event) {
        List<PrgState> states = controller.getRepository().getPrgList();
        int index = prgStateListView.getSelectionModel().getSelectedIndex();
        PrgState program = states.get(index);
        populateExeStackListView(program);
        populateSymTableTableView(program);
    }
}