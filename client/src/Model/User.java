package Model;

public class User
{
  private Username username;
  private Password password;

  public User(Username username, Password password)
  {
    this.username = username;
    this.password = password;
  }

  public Username getUsername()
  {
    return username;
  }

  public Password getPassword()
  {
    return password;
  }

  public String getUsernameString()
  {
    return username.getUsername();
  }

  public String getPasswordString()
  {
    return password.getPassword();
  }
}
