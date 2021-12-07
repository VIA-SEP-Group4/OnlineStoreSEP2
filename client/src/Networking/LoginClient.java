package Networking;

import Model.Models.Customer;
import Model.Models.Employee;
import Utils.Subject;

public interface LoginClient extends Subject {
    void registerUser(Customer newCustomer);
    void loginCustomer(String username, String password) ;
    void startClient();
    Customer getLoggedCustomer();
    Employee getLoggedEmployee();
    void loginEmployee(int ID, int pin);
    boolean isStarted();
    void editEmployee(Employee e);
}
