package Model;

import Model.Models.Customer;
import Model.Models.Employee;
import Utils.Subject;

import java.util.ArrayList;

public interface CredentialsModel extends Subject
{

  ArrayList<Employee> getManagers();
  ArrayList<Employee> getWorkers();

  void registerCustomer(Customer newUser);
  void registerEmployee(Employee employee);
  void removeEmployee(Employee e);
  void editEmployee(Employee e);

  Customer loginCustomer(String username, String password);
  Employee loginEmployee(int ID, int pin);

  void deleteCustomer(int customerId);
  void editCustomer(Customer editedCustomer);
}
