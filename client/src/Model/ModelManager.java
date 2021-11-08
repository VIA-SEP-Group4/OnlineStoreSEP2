package Model;

import Networking.Client;

public class ModelManager implements Model
{

  private Client client;
  public ModelManager(Client client)
  {
    this.client = client;
  }

  @Override public void sendMessage(String message)
  {
    client.sendMessage(message);
  }

  @Override public void toUpperCase(String text)
  {
    System.out.println("Input: " + text);
    client.toUpperCase(text);
  }
}
