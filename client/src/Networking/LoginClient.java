package Networking;

import Model.Customer;
import Model.Employee;
import Utils.Subject;

public interface LoginClient extends Subject {
    void registerUser(Customer newCustomer);
    void loginCustomer(String username, String password) ;
    void startClient();
    Customer getLoggedCustomer();
    Employee getLoggedEmployee();
    void loginEmployee(int ID, int pin);
}
