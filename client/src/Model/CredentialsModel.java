package Model;

import Utils.Subject;

public interface CredentialsModel extends Subject
{
  void login(String username, String password,String type);
  void registerUser(Customer newCustomer);
  Customer getLoggedUser();

}
