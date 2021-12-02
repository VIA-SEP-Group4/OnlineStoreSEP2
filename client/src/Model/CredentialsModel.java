package Model;

import Utils.Subject;

import java.util.ArrayList;

public interface CredentialsModel extends Subject
{
  void login(String username, String password,String type);
  void registerUser(User newUser);
  User getLoggedUser();

}
