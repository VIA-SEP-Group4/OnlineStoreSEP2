package Model;
import Model.Models.Customer;
import Model.Models.Employee;
import Networking.CredentialsClient;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class CredentialsModelManager implements CredentialsModel, PropertyChangeListener
{

  private CredentialsClient credentialsClient;
  private PropertyChangeSupport support;


  public CredentialsModelManager(CredentialsClient client)
  {
    support=new PropertyChangeSupport(this);
    this.credentialsClient = client;
    credentialsClient.startClient();
    credentialsClient.addListener("LoginReply",this);
    credentialsClient.addListener("RegistrationReply",this);
    credentialsClient.addListener("AddedWorker",this);
    credentialsClient.addListener("ManagerAddReply",this);
    credentialsClient.addListener("AdminReply",this);
    credentialsClient.addListener("ManagerWorkersReply",this);
    credentialsClient.addListener("LoggedCustomerObj",this);
  }


  @Override
  public void login(String username, String password, String type) {
    if(type.equals("Customer")){
      credentialsClient.loginCustomer(username,password);
    }
    else if(type.equals("Employee")){
      credentialsClient.loginEmployee(Integer.parseInt(username),Integer.parseInt(password));
    }
  }

  @Override public void registerUser(Customer newCustomer)
  {
    credentialsClient.registerUser(newCustomer);
  }

  @Override
  public Customer getLoggedCustomer() {
    return credentialsClient.getLoggedCustomer();
  }

  @Override
  public Employee getLoggedEmployee() {
    return credentialsClient.getLoggedEmployee();
  }

  @Override
  public void addEmployee(Employee e) {
    credentialsClient.addEmployee(e);
  }

  @Override
  public void removeEmployee(Employee e) {
    credentialsClient.removeEmployee(e);
  }

  @Override
  public void editEmployee(Employee e) {
    credentialsClient.editEmployee(e);
  }

  @Override
  public ArrayList<Employee> getEmployees(String type) {
    return credentialsClient.getEmployees(type);
  }

  @Override public void editCustomer(Customer editedCustomer)
  {
    credentialsClient.editCustomer(editedCustomer);
  }

  @Override public void deleteCustomer()
  {
    credentialsClient.deleteCustomer();
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
