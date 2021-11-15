package DataAcess;

import Model.*;
import Utils.Subject;

import java.util.ArrayList;

public interface DataAccessor extends Subject
{
  void registerUser(User newUser);
  void loginUser(String username, String password, String clientID);
  int getUserCount();
  ArrayList<User> getUsers();
}
