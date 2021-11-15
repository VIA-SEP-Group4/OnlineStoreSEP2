package Model;

import Networking.Client;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class LoginModelManager implements LoginModel, PropertyChangeListener
{

  private Client client;
  private PropertyChangeSupport support;
  public LoginModelManager(Client client)
  {
    support=new PropertyChangeSupport(this);
    this.client = client;
    client.startClient();
    client.addListener("LoginReply",this);
  }


  @Override
  public void login(String username, String password) {
    client.loginUser(username,password);
  }

  @Override public void registerUser(User newUser)
  {
    client.registerUser(newUser);
  }

  @Override public int getNumberOfUsers()
  {
    return client.getNumberOfUsers();
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    support.firePropertyChange(evt);
  }

  @Override
  public void addListener(String eventName, PropertyChangeListener listener) {
    support.addPropertyChangeListener(eventName,listener);
  }

  @Override
  public void removeListener(String eventName, PropertyChangeListener listener) {
    support.removePropertyChangeListener(listener);
  }
}
