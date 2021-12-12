package DataAcess;

import Model.Models.Customer;
import Model.Models.Employee;
import Utils.Subject;

import java.util.ArrayList;

public interface CredentialsDataAccessor extends Subject
{
  void registerCustomer(Customer newUser);
  void registerEmployee(Employee newUser);
  Customer loginCustomer(String username, String password);
  ArrayList<Employee> getEmployees();
  Employee loginEmployee(int ID, int pin);

  void editEmployee(Employee e);
  void removeEmployee(Employee e);
  void deleteCustomer(int customerId);
  void editCustomer(Customer editedCustomer);
}
