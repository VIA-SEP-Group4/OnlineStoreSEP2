package Model;
import Networking.LoginClient;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class CredentialsModelManager implements CredentialsModel, PropertyChangeListener
{

  private LoginClient client;
  private PropertyChangeSupport support;


  public CredentialsModelManager(LoginClient client)
  {
    support=new PropertyChangeSupport(this);
    this.client = client;
    client.startClient();
    client.addListener("LoginReply",this);
    client.addListener("RegistrationReply",this);
    client.addListener("BrowserReply",this);
  }


  @Override
  public void login(String username, String password, String type) {
    if(type.equals("Customer")){
      client.loginCustomer(username,password);
    }
    else if(type.equals("Employee")){
      client.loginEmployee(Integer.parseInt(username),Integer.parseInt(password));
    }
  }

  @Override public void registerUser(Customer newCustomer)
  {
    client.registerUser(newCustomer);
  }

  @Override
  public Customer getLoggedCustomer() {
    return client.getLoggedCustomer();
  }

  @Override
  public Employee getLoggedEmployee() {
    return client.getLoggedEmployee();
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
