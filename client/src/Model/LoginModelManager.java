package Model;

import Networking.Client;

public class LoginModelManager implements LoginModel
{

  private Client client;
  public LoginModelManager(Client client)
  {
    this.client = client;
  }



  @Override public void registerUser(User newUser)
  {
    client.registerUser(newUser);
  }

  @Override public int getNumberOfUsers()
  {
    return client.getNumberOfUsers();
  }
}
