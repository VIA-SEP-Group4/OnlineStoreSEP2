package Model;

import Utils.Subject;

public interface LoginModel extends Subject
{

  void login(String username, String password);
  void registerUser(User newUser);
  int getNumberOfUsers();
}
