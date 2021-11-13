package DataAcess;

import Model.User;

public interface DataAccessor
{
  void registerUser(User newUser);
  void loginUser(User loggingUser);
  int getUserCount();
}
