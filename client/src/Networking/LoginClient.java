package Networking;

import Model.User;
import Utils.Subject;

public interface LoginClient extends Subject {
    void registerUser(User newUser);
    void loginUser(String username, String password,String type) ;
    void startClient();
    User getLoggedUser();
}
