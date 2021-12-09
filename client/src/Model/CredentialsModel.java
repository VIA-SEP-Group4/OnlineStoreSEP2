package Model;

import Model.Models.Customer;
import Model.Models.Employee;
import Utils.Subject;

public interface CredentialsModel extends Subject
{
  void login(String username, String password,String type);
  void registerUser(Customer newCustomer);
  Customer getLoggedCustomer();
  Employee getLoggedEmployee();

  void editCustomer(Customer editedCustomer);
  void deleteCustomer();
}
