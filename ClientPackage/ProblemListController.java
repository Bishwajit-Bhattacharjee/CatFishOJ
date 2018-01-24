package ClientPackage;


import Common.Problem;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

//Client
public class ProblemListController {

    private CatfishClient catfishClient;

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
        // Initialize the person table with the two columns.
        IDColumn.setCellValueFactory(cellData -> cellData.getValue().getProblemIDProperty());
        ProblemNameColumn.setCellValueFactory(cellData -> cellData.getValue().getProblemNameProperty());

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
        problemTable.setItems(Problem.getProblemListObservable());
    }
}
