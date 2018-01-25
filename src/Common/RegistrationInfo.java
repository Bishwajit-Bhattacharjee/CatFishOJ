package Common;


import java.io.Serializable;

//Server + Client
public class RegistrationInfo implements Serializable{
    private String userName , password ;


    public RegistrationInfo(String userName, String password) {
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
