package DataAcess;

import Model.*;
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
}
