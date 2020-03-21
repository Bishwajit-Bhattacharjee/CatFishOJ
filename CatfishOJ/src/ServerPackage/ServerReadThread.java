package ServerPackage;

import java.util.HashMap;

import Common.ClientSubmit;
import Common.LoginInfo;
import Common.NetworkUtil;
import Common.RegistrationInfo;

//Server
public class ServerReadThread implements Runnable{

    private NetworkUtil nc;
    private int clientID;
    private HashMap<Integer, NetworkUtil> clientMap;
    Thread thread;

    public ServerReadThread(NetworkUtil nc , int clientID , HashMap<Integer, NetworkUtil> clientMap) {
        this.nc = nc;
        this.clientID = clientID;
        this.clientMap = clientMap;
        this.thread = new Thread(this);

        thread.start();
    }

    public void run() {

        try {
            while(true) {

                Object object = nc.read();
                if( object != null) {
                    //System.out.println("is something");
                    if(object instanceof RegistrationInfo) {
                        //synchronized ((RegistrationInfo)object) {
                            RegistrationInfo validator = (RegistrationInfo) object;
                            RegistrationValidator registrationValidator = new RegistrationValidator(validator) ;

                            //System.out.println("hello from registration ");
                            nc.write(registrationValidator.returnRegistrationValidator());

                       // }

                    }
                    else if( object instanceof LoginInfo) {
                     //   synchronized ((LoginValidator)object) {
                            LoginInfo validator = (LoginInfo) object;
                            LoginValidator loginValidator = new LoginValidator(validator) ;

                            nc.write(loginValidator.returnLoginValue());
                     //   }
                    }

                    else if(object instanceof ClientSubmit) {

                        ClientSubmit validator = (ClientSubmit) object;
                        //System.out.println("hello form readthread" + validator.getSubmitLanguage());
                        Verdict verdict = new Verdict(validator);
                        CatfishServer.updateUserInfoFromVerdict(verdict.toString(), validator.getCurrentUser(), validator.getCurrentProblem()) ;
                        //System.out.println(verdict.toString());
                        nc.write(verdict.toString()) ;
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //System.out.println("finallyy!!!!!");
            nc.closeConnection();
        }
    }
}
