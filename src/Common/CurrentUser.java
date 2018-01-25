package Common;

import java.io.Serializable;

public class CurrentUser implements Serializable{
    User currentUser;

    public CurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
