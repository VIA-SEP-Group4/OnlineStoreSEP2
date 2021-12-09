package Model;

import Model.Models.Customer;
import Model.Models.Employee;
import Utils.Subject;

import java.util.ArrayList;

public interface CredentialsModel extends Subject
{
  void login(String username, String password,String type);
  void registerUser(Customer newCustomer);
  Customer getLoggedCustomer();
  Employee getLoggedEmployee();
  void addEmployee(Employee e);
  void removeEmployee(Employee e);
  void editEmployee(Employee e);
  ArrayList<Employee> getEmployees(String type);
  void editCustomer(Customer editedCustomer);
  void deleteCustomer();
}
