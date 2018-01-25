package Common;

import java.io.Serializable;

//Server
public class LoginInfo implements Serializable{
    private String userName , password ;


    public LoginInfo(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
