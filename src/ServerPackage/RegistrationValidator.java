package ServerPackage;

import java.io.Serializable;
import Common.RegistrationInfo;
import Common.User;

//Server
public class RegistrationValidator implements Serializable{
    private String userName , password ;


    public RegistrationValidator(RegistrationInfo registrationInfo) {
        this.userName = registrationInfo.getUserName();
        this.password = registrationInfo.getPassword();
    }

    /**
     *
     * @return 1 -> when space is found
     *         2 -> when null is provided
     *         3 -> the handle is not available
     *         4 -> successful registration
     */
    public int returnRegistrationValidator()
    {
        for(int i =0 ; i < userName.length() ; i++){
            if(userName.charAt(i) == ' ' ) {
                return 1 ; // space is found

            }
        }
        if( userName.length()==0 || password.length()==0) {
            return 2 ; // null is provided
        }


        for (User user : CatfishServer.userRecords) {
            String stringTmp = user.getHandle();

            if (stringTmp.equalsIgnoreCase(userName)) {

                return 3 ;
                //break;
            }

        }
        CatfishServer.addUser(new User(userName, password , 0,0,0,0,0));
        CatfishServer.writeFile();
        return 4 ;

    }

}
