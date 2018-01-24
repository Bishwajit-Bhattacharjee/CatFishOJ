package ClientPackage;

import Common.LoginInfo;
import Common.User;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.util.Duration;
import org.controlsfx.control.Notifications;


//Client
public class LoginController {
    private CatfishClient catfishClient;

    @FXML
    private JFXTextField handleField ;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private void RegistrationButtonClicked() {

        try {
            catfishClient.showRegistrationMenu();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void LoginButtonClicked(){

        String userName = handleField.getText();
        String password = passwordField.getText();


        /**
         * Server will handle from this block
         */
        //reference to the actual UserRecords :D

        catfishClient.nc.write(new LoginInfo(userName, password));
        Object o ;
        User typeOfValidator ;

        while(true)
        {
            o = catfishClient.nc.read() ;
            if( o != null) {
                if(o instanceof User){
                    typeOfValidator = (User) o;
                    break ;
                }
            }
        }



        if(!typeOfValidator.getHandle().equals(" "))
        {
            catfishClient.setCurrentUser(typeOfValidator) ;
            Notifications notifications = Notifications.create()
                    .title("Confirmation!")
                    .text("Successfully Logged in")
                    .graphic(null)
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.TOP_RIGHT);
            notifications.showConfirm();

            try{
                catfishClient.showProblemList();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            Notifications notifications = Notifications.create()
                    .title("Login Failed!")
                    .text("This user is not available")
                    .graphic(null)
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.TOP_RIGHT);
            notifications.showError();

        }

    }

    public void setCatfishClient(CatfishClient catfishClient) {
        this.catfishClient = catfishClient;
    }

}
