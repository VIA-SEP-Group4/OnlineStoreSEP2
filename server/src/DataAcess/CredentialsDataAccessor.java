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
  int getUserCount();
  ArrayList<Employee> getEmployees();
  Employee loginEmployee(int ID, int pin);

    void removeEmployee(Employee e);
}
