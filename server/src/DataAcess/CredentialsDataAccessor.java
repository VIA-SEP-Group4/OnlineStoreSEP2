package DataAcess;

import Model.*;
import Utils.Subject;

import java.util.ArrayList;

public interface CredentialsDataAccessor extends Subject
{
  void registerUser(User newUser);
  void loginUser(String username, String password, String selectedUserType);
  int getUserCount();
  ArrayList<User> getUsers();

}
