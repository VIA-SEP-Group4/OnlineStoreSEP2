package Model;
import Model.Models.Customer;
import Model.Models.Employee;
import Networking.LoginClient;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class CredentialsModelManager implements CredentialsModel, PropertyChangeListener
{

  private LoginClient loginClient;
  private PropertyChangeSupport support;


  public CredentialsModelManager(LoginClient client)
  {
    support=new PropertyChangeSupport(this);
    this.loginClient = client;
    loginClient.startClient();
    loginClient.addListener("LoginReply",this);
    loginClient.addListener("RegistrationReply",this);
    loginClient.addListener("BrowserReply",this);
  }


  @Override
  public void login(String username, String password, String type) {
    if(type.equals("Customer")){
      loginClient.loginCustomer(username,password);
    }
    else if(type.equals("Employee")){
      loginClient.loginEmployee(Integer.parseInt(username),Integer.parseInt(password));
    }
  }

  @Override public void registerUser(Customer newCustomer)
  {
    loginClient.registerUser(newCustomer);
  }

  @Override
  public Customer getLoggedCustomer() {
    return loginClient.getLoggedCustomer();
  }

  @Override
  public Employee getLoggedEmployee() {
    return loginClient.getLoggedEmployee();
  }

  @Override public void editCustomer(Customer editedCustomer)
  {
    loginClient.editCustomer(editedCustomer);
  }

  @Override public void deleteCustomer()
  {
    loginClient.deleteCustomer();
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
