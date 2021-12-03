package DataAcess;

import Model.*;
import Utils.Subject;

import java.util.ArrayList;

public interface CredentialsDataAccessor extends Subject
{
  void registerCustomer(Customer newUser);
  void registerEmployee(Employee newUser);
  Customer loginUser(String username, String password, String selectedUserType);
  int getUserCount();
  ArrayList<Customer> getUsers();

}
