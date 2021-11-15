package Model;

import Utils.Subject;

public interface Model extends Subject
{
  void registerUser(User newUser);
  void loginUser(String username, String password);
  int userCount();
}
