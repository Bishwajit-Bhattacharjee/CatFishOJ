package ClientPackage;

import Common.RegistrationInfo;
import Common.User;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.geometry.Pos;



import javafx.util.Duration;
import org.controlsfx.control.Notifications;


//Client
public class RegistrationController  {

    private CatfishClient catfishClient;


    @FXML
    private JFXTextField handleField;

    @FXML
    private JFXPasswordField passwordField ;

    @FXML
    /**
     *  1.isValid boolean variable true -> "THis registration is valid"
     * We also gave a check for the null string for both handle and password ...
     * 2. variable hasSpace keeps track whether there's a space in handle , which is
     * considered as invalid handle
     * */
    private void RegisterButtonClicked() {
        String userName = handleField.getText();
        String password = passwordField.getText();



        catfishClient.nc.write(new RegistrationInfo(userName , password) );
        Integer typeOfVerdict;

        while(true) {
            Object o = catfishClient.nc.read();
            if( o != null)
                if( o instanceof Integer) {
                    //System.out.println("arre kop!!");
                    typeOfVerdict = (Integer) o;
                    break;
                }
        }


        if(typeOfVerdict == 1)
        {
            Notifications notifications = Notifications.create()
                    .title("Registration Failed!")
                    .text("Handle cannot have any space")
                    .graphic(null)
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.TOP_RIGHT);
            notifications.showWarning();
        }
        else if(typeOfVerdict == 2 )
        {
            Notifications notifications = Notifications.create()
                    .title("Registration Failed!")
                    .text("Please Enter a Valid Handle and Password.")
                    .graphic(null)
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.TOP_RIGHT);
            notifications.showWarning();

        }
        else if(typeOfVerdict == 3 )
        {

            Notifications notifications = Notifications.create()
                    .title("Registration Failed!")
                    .text("The Handle is not available.")
                    .graphic(null)
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.TOP_RIGHT);
            notifications.showWarning();

        }
        else {
            catfishClient.setCurrentUser(new User(userName, password , 0 , 0 , 0 , 0,0)) ;
            Notifications notifications = Notifications.create()
                    .title("Confirmation!")
                    .text("Successfully Registered.")
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
    }

    @FXML
    private void LoginButtonClicked() {

        try {
            catfishClient.showLoginMenu();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    void setCatfishClient(CatfishClient catfishClient) {
        this.catfishClient = catfishClient;
    }
}
