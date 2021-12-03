package Networking;

import Model.Customer;
import Utils.Subject;

public interface LoginClient extends Subject {
    void registerUser(Customer newCustomer);
    void loginUser(String username, String password,String type) ;
    void startClient();
    Customer getLoggedUser();
}
