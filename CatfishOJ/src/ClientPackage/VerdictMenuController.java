package ClientPackage;

import Common.Problem;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

//Client
public class VerdictMenuController{

    private CatfishClient catfishClient;
    private Problem problem;

    private String HandleLabel;
    private String ProblemNameLabel;
    private String TimeLabel;
    private String VerdictLabel;
    private String LanguageLabel;

    @FXML
    private JFXButton HandleButton;

    @FXML
    private JFXButton ProblemButton;

    @FXML
    private JFXButton TimeButton;

    @FXML
    private JFXButton VerdictButton;

    @FXML
    private JFXButton LanguageButton;
    @FXML
    private void BackButtonClicked() {
        try {
            catfishClient.showProblem(problem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private  void SubmitButtonClicked() {
        try {
            catfishClient.showSubmitMenu(problem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void UserStatisticsClicked() {

        try {
            catfishClient.showUserStatisticsMenu();
        } catch (Exception e ) {
            e.printStackTrace();
        }
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
    private void ExitButtonClicked() {

        try {
            System.exit(0);
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }


    @FXML
    private void TasksButtonClicked() {

        try {
            catfishClient.showProblemList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void process() {

//        Verdict verdict = new Verdict(problem, main.getCurrentUser());
        Object o ;
        String[] fullInfo;

        while(true) {
            o = catfishClient.nc.read();

            if(o != null) {
                if( o instanceof  String) {
                    System.out.println(o);
                    fullInfo = ((String) o).split(",");
                    break;
                }
            }
        }

        int exitVal = Integer.parseInt(fullInfo[0]);
        double cpuTime = 0;
        String str;
        if( exitVal != 0)
            str = "Compilation Error";
        else {

            exitVal = Integer.parseInt(fullInfo[1]);
            if( exitVal != 0) {
                str = "Time Limit Exceeded";
            }
            else if( Integer.parseInt(fullInfo[3]) == 1) {
                str = "Wrong Answer";
            }
            else
                str = "Accepted";
        }
        cpuTime = Double.parseDouble(fullInfo[2]);

        VerdictLabel = (str);
        ProblemNameLabel = (problem.getProblemID() + " - " + problem.getProblemName() );
        LanguageLabel = fullInfo[4];
        HandleLabel = (catfishClient.getCurrentUser().getHandle() );
        TimeLabel = (Double.toString(cpuTime));

        setup();
    }

    private void setup() {

        HandleButton.setText(HandleLabel);
        ProblemButton.setText(ProblemNameLabel);
        TimeButton.setText(TimeLabel);
        LanguageButton.setText(LanguageLabel);

        if( VerdictLabel.equals("Accepted") ) {

            VerdictButton.setText("Accepted");
            VerdictButton.setStyle("-fx-text-fill: #006400");
        }
        else if(VerdictLabel.equals("Compilation Error")) {

            VerdictButton.setText("Compilation Error");
            VerdictButton.setStyle("-fx-text-fill: #2d3447");
        }
        else if(VerdictLabel.equals("Time Limit Exceeded")) {
            VerdictButton.setText("Time Limit Exceeded!!");
            VerdictButton.setStyle("-fx-text-fill: #bd2031");
        }
        else if(VerdictLabel.equals("Wrong Answer")) {
            VerdictButton.setText("Time Limit Exceeded!!");
            VerdictButton.setStyle("-fx-text-fill: #bd2031");
        }
    }

    public void setCatfishClient(CatfishClient catfishClient) {
        this.catfishClient = catfishClient;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
        process();
    }


}
