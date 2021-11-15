package Model;

import Utils.Subject;

import java.beans.PropertyChangeListener;

public interface Model extends Subject
{
  void registerUser(User newUser);
  void loginUser(User loggingUser);
  int userCount();

}
