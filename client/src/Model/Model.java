package Model;

public interface Model
{
  void sendMessage(String message);
  void toUpperCase(String text);

  void registerUser(User newUser);
  int getNumberOfUsers();
}
