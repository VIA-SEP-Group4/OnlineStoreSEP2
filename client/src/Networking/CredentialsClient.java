package Networking;

import Model.Models.Customer;
import Model.Models.Employee;
import Utils.Subject;

import java.util.ArrayList;

public interface CredentialsClient extends Subject {
    void registerUser(Customer newCustomer);
    void loginCustomer(String username, String password) ;
    void startClient();
    Customer getLoggedCustomer();
    Employee getLoggedEmployee();
    void loginEmployee(int ID, int pin);
    void editEmployee(Employee e);
    void addEmployee(Employee e);
    void removeEmployee(Employee e);
    ArrayList<Employee> getEmployees(String type);
    //TODO ... added 9.12.12:50
    void deleteCustomer();
    void editCustomer(Customer editedCustomer);
}
