package Common;


import java.io.Serializable;
//Server + Client
public class ClientSubmit implements Serializable {
    private String solution ;
    private User currentUser;
    private Problem currentProblem;
    private String submitLanguage ;

    public ClientSubmit(String solution, User currentUser, Problem currentProblem, String submitLanguage) {
        this.solution = solution;
        this.currentUser = currentUser;
        this.currentProblem = currentProblem;
        this.submitLanguage = submitLanguage ;
    }

    public String getSubmitLanguage() {
        return submitLanguage;
    }

    public String getSolution() {
        return solution;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Problem getCurrentProblem() {
        return currentProblem;
    }
}
