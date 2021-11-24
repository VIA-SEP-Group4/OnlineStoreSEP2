package Model;

import Networking.Client;
import Utils.Subject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class CredentialsModelManager implements CredentialsModel, PropertyChangeListener
{

  private Client client;
  private PropertyChangeSupport support;


  public CredentialsModelManager(Client client)
  {
    support=new PropertyChangeSupport(this);
    this.client = client;
    client.startClient();
    client.addListener("LoginReply",this);
    client.addListener("RegistrationReply",this);
    client.addListener("BrowserReply",this);
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
