package ClientPackage;


import Common.Problem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;

//Client
public class ProblemListController {

    private CatfishClient catfishClient;
    private ArrayList<Problem> problemList;
    ObservableList<Problem> list;
    @FXML
    TableView<Problem> problemTable;

    @FXML
    private TableColumn<Problem, String> IDColumn;

    @FXML
    private TableColumn<Problem, String> ProblemNameColumn;

    @FXML
    private TableColumn<Problem, String> SolvedByTriedColumn;

    @FXML
    private TableColumn<Problem, String> AcceptedByTotalColumn;

    @FXML
    private void initialize() {

        problemList = Problem.problemList;
        System.out.println(Problem.problemList);

        list = FXCollections.observableArrayList(problemList);
        problemTable.setItems(list);
        // Initialize the person table with the two columns.
        IDColumn.setCellValueFactory(cellData -> cellData.getValue().getProblemIDProperty());

        ProblemNameColumn.setCellValueFactory(cellData -> cellData.getValue().getProblemNameProperty());

        SolvedByTriedColumn.setCellValueFactory(cellData -> cellData.getValue().getSolvedByTried());

        AcceptedByTotalColumn.setCellValueFactory(cellData -> cellData.getValue().getAcceptedByTotal());

        problemTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showProblemDetails(newValue));


    }

    @FXML
    private void LogoutButtonClicked() {

        try {
            catfishClient.showRegistrationMenu();
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }

    @FXML
    private void UserStatisticsClicked() {
        try {
            catfishClient.showUserStatisticsMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void ExitButtonClicked() {

        try {
            System.exit(0);
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }



    private void showProblemDetails(Problem problem) {

        try {
            if (problem != null) {
                catfishClient.showProblem(problem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setCatfishClient(CatfishClient catfishClient) {
        this.catfishClient = catfishClient;
    }
}
