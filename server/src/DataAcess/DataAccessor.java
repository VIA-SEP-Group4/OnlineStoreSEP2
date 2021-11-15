package DataAcess;

import Model.*;
import Utils.Subject;

public interface DataAccessor extends Subject
{
  void registerUser(User newUser);
  void loginUser(String username, String password);
  int getUserCount();
}
