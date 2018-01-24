package ServerPackage;

import Common.LoginInfo;
import Common.User;

import java.io.Serializable;
//Server
public class LoginValidator implements Serializable{
    private String userName , password ;


    public LoginValidator(LoginInfo loginInfo) {
        this.userName = loginInfo.getUserName();
        this.password = loginInfo.getPassword();

    }

    public User returnLoginValue() {
        for (User user : CatfishServer.userRecords) {
            if (userName.equalsIgnoreCase(user.getHandle()) && password.equals(user.getPassword())) {
                return user ;
            }
        }
        return new User(" " , " " , 0,0,0,0,0 ) ; // " "  is used for checking null

    }
}
