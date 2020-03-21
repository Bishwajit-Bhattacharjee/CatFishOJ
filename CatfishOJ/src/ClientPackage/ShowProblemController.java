package ClientPackage;
import java.io.File;

import Common.Problem;
import com.jfoenix.controls.JFXTextArea;
import javafx.scene.control.Label;
import java.util.Scanner;
import javafx.fxml.FXML;


//Client
public class ShowProblemController {


    private CatfishClient catfishClient;
    private String fileName;
    private Problem problem;

    public void setProblem(Problem problem) {
        this.problem = problem;
        setFile();
    }

    @FXML
    private JFXTextArea ProblemTextArea;

    @FXML
    private Label ProblemNameLabel;

    public void setProblemNameLabel(String problemName) {
        ProblemNameLabel.setText(problemName);
    }

    @FXML
    private void TasksButtonClicked() {

        try {
            catfishClient.showProblemList();
        } catch (Exception e) {
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
    private  void SubmitButtonClicked() {
        try {
            catfishClient.showSubmitMenu(problem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setFileName() {
        fileName = problem.getProblemID();
        fileName = fileName + ".txt";
    }

    @FXML
    private void UserStatisticsClicked() {

        try {
            catfishClient.showUserStatisticsMenu();
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }

    public void setFile() {
        setProblemNameLabel(problem.getProblemName());
        setFileName();
        try {
            Scanner input = new Scanner( new File(fileName));

            while (input.hasNextLine()) {
                ProblemTextArea.appendText(input.nextLine() + "\n"); // else read the next token
            }
            input.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setCatfishClient(CatfishClient catfishClient) {
        this.catfishClient = catfishClient;
    }
}
