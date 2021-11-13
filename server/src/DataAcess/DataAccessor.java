package DataAcess;

import Model.*;

public interface DataAccessor
{
  void registerUser(User newUser);
  void loginUser(User loggingUser);
  int getUserCount();
}
