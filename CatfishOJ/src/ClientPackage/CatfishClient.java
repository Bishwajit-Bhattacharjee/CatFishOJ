package ClientPackage;

import Common.NetworkUtil;
import Common.Problem;
import Common.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

//Client
public class CatfishClient extends Application {

    Stage stage;
    private User currentUser;
    public NetworkUtil nc ;
    private int clientID;
    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        //Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        //primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        //primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 3);
        stage = primaryStage;

        stage.setResizable(false);
        //stage.initStyle(StageStyle.TRANSPARENT);
        //stage.setAlwaysOnTop(true);
        nc = new NetworkUtil("127.0.0.1", 3333);
        clientID = (Integer) nc.read();
        showRegistrationMenu();
    }

    public void showRegistrationMenu() throws Exception {

        FXMLLoader loader  = new FXMLLoader();
        loader.setLocation(getClass().getResource("Registration.fxml") );
        Parent root = loader.load();

        RegistrationController controller = loader.getController();
        controller.setCatfishClient(this);

        stage.setScene(new Scene(root) );
//        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
//        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
//        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        stage.centerOnScreen();
        stage.show();

    }

    public void showLoginMenu() throws Exception{
        FXMLLoader loader  = new FXMLLoader();
        loader.setLocation(getClass().getResource("Login.fxml") );
        Parent root = loader.load();

        LoginController controller = loader.getController();
        controller.setCatfishClient(this);
        stage.setScene(new Scene(root) );
//        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
//        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
//        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);
        stage.show();


    }

    public void showProblemList() throws Exception{
        FXMLLoader loader  = new FXMLLoader();
        loader.setLocation(getClass().getResource("ProblemList.fxml") );
        Parent root = loader.load();

        ProblemListController controller = loader.getController();
        controller.setCatfishClient(this);
        //stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(new Scene(root) );
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);

        stage.show();
    }

    public void showProblem(Problem problem) throws Exception {
        FXMLLoader loader  = new FXMLLoader();
        loader.setLocation(getClass().getResource("ShowProblem.fxml") );
        Parent root = loader.load();

        ShowProblemController controller = loader.getController();
        controller.setCatfishClient(this);
        controller.setProblem(problem);//Problem instance created in showproblencontroller class

        stage.setScene(new Scene(root) );
        stage.show();
//        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
//        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
//        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);

    }

    public void showSubmitMenu(Problem problem) throws Exception {
        FXMLLoader loader  = new FXMLLoader();
        loader.setLocation(getClass().getResource("SubmitMenu.fxml") );
        Parent root = loader.load();

        SubmitMenuController controller = loader.getController();
        controller.setCatfishClient(this);

        controller.setProblem(problem);
        stage.setScene(new Scene(root) );
        stage.show();
//        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
//        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
//        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);

    }

    public void showUserStatisticsMenu() throws Exception {
        FXMLLoader loader  = new FXMLLoader();
        loader.setLocation(getClass().getResource("UserStatistics.fxml") );
        Parent root = loader.load();

        UserStatisticsController controller = loader.getController();
        controller.setCatfishClient(this);
        controller.setUser(currentUser);
        stage.setScene(new Scene(root) );
        stage.show();
//        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
//        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
//        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);
    }



    public void showVerdictMenu(Problem problem) throws Exception {
        FXMLLoader loader  = new FXMLLoader();
        loader.setLocation(getClass().getResource("VerdictMenu.fxml") );
        Parent root = loader.load();
        VerdictMenuController controller = loader.getController();
        controller.setCatfishClient(this);

        controller.setProblem(problem);
        stage.setScene(new Scene(root));
        stage.show();
//        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
//        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
//        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);

    }

    public static void main(String[] args) {
        Problem.init();
        launch(args);
    }
}
