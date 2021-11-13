package Model;

public interface Model
{
  String toUpperCase(String text);

  void registerUser(User newUser);
  void loginUser(User loggingUser);
  int userCount();
}
