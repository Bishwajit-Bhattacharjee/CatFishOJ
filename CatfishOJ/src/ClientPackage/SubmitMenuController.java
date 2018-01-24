package ClientPackage;

import Common.ClientSubmit;
import Common.Problem;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

//Client
public class SubmitMenuController {

    //private static final String SUBMITTED_FILE_NAME_C = "userSolution.c";

    private CatfishClient catfishClient;
    private Problem problem;
    private String language = " ";
    @FXML
    private JFXTextArea SolutionTextArea;

    @FXML
    private Label ProblemNameLabel;
    @FXML
    private Label fileName;

    public void setProblem(Problem problem) {
        this.problem = problem;
        ProblemNameLabel.setText(problem.getProblemName());
    }

    @FXML
    private JFXComboBox chooseLanguage;

    ObservableList<String>languageList = FXCollections
            .observableArrayList("C", "C++", "Java");

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
    private void UploadButtonClicked() {
        String textCode=null;
        if( language.equals(" ") ) {

            Notifications notifications = Notifications.create()
                    .title("Language not selected!")
                    .text("Please select a language from the dropdown.")
                    .graphic(null)
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.TOP_RIGHT);
            notifications.showWarning();

        }
        else {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.c", "*.cpp", "*.java"));
            File file = fc.showOpenDialog(null);
            if( file != null) {
                fileName.setText(file.getName());
                Notifications notifications = Notifications.create()
                        .title("Confirmation!")
                        .text("Your Code has been successfully submitted.")
                        .graphic(null)
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.TOP_RIGHT);
                notifications.showConfirm();

            }
            else return;

            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getName()));
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                textCode = sb.toString();
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //System.out.println("hello from UploadButtonClicked");
            //System.out.println(textCode);
            catfishClient.nc.write(new ClientSubmit(textCode, catfishClient.getCurrentUser(), problem, language) );
            try {
                catfishClient.showVerdictMenu(problem);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
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
    private void ProblemButtonClicked() {

        try {
            catfishClient.showProblem(problem);
        } catch (Exception e) {
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

    @FXML
    private void SubmitButtonClicked() {

        if( !language.equals(" ") ) {
            System.out.println("Clicked");
            Notifications notifications = Notifications.create()
                    .title("Confirmation!")
                    .text("Your Code has been successfully submitted.")
                    .graphic(null)
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.TOP_RIGHT);
            notifications.showConfirm();

            String textCode = SolutionTextArea.getText();
            System.out.println(language);
            catfishClient.nc.write(new ClientSubmit(textCode, catfishClient.getCurrentUser(), problem, language) );
            try {
                catfishClient.showVerdictMenu(problem);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else {

            Notifications notifications = Notifications.create()
                    .title("Language not selected!")
                    .text("Please select a language from the dropdown.")
                    .graphic(null)
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.TOP_RIGHT);
            notifications.showWarning();
        }
    }

    @FXML
    private void comboButtonClicked() {

        language = (String)chooseLanguage.getValue();
    }

    public void setCatfishClient(CatfishClient catfishClient) {
        this.catfishClient = catfishClient;
    }
    @FXML
    private void initialize() {

        chooseLanguage.setItems(languageList);
    }
}
