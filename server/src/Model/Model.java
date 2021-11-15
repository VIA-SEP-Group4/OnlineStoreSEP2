package Model;

import Utils.Subject;

public interface Model extends Subject
{
  String toUpperCase(String text);

  void registerUser(User newUser);
  void loginUser(String username, String password, String clientID);
  int userCount();
}
