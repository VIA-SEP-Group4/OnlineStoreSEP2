package Model;

import DataAcess.*;
import Enums.EmployeeType;
import Model.Models.Customer;
import Model.Models.Employee;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class CredentialsModelManager implements CredentialsModel, PropertyChangeListener
{
  private CredentialsDataAccessor credentialsDataAccessor;
  private OrdersDataAccessor ordersDataAccessor;

  private PropertyChangeSupport support;

  public CredentialsModelManager()
  {
    credentialsDataAccessor = new CredentialsDataManager();
    ordersDataAccessor = new OrdersDataManager();

    support = new PropertyChangeSupport(this);

    //listeners
    credentialsDataAccessor.addListener("AdminReply",this);
    credentialsDataAccessor.addListener("ManagerReply",this);
  }


  @Override public void registerCustomer(Customer newUser)
  {
    credentialsDataAccessor.registerCustomer(newUser);
  }
  @Override public Customer loginCustomer(String username, String password)
  {
    Customer loggedCustomer = credentialsDataAccessor.loginCustomer(username,password);
    loggedCustomer.setOrders(ordersDataAccessor.getOrders(loggedCustomer.getCustomerId()));
    return loggedCustomer;
  }

  @Override
  public void registerEmployee(Employee employee) {
    credentialsDataAccessor.registerEmployee(employee);
  }
  @Override
  public Employee loginEmployee(int ID, int pin) {
    return credentialsDataAccessor.loginEmployee(ID,pin);
  }

  @Override public void deleteCustomer(int customerId)
  {
    credentialsDataAccessor.deleteCustomer(customerId);
  }

  @Override public void editCustomer(Customer editedCustomer)
  {
    credentialsDataAccessor.editCustomer(editedCustomer);
  }

  @Override
  public void removeEmployee(Employee e) {
    credentialsDataAccessor.removeEmployee(e);
  }

  @Override
  public void editEmployee(Employee e) {
    credentialsDataAccessor.editEmployee(e);
  }


  @Override public int userCount()
  {
    return credentialsDataAccessor.getUserCount();
  }


  @Override
  public ArrayList<Employee> getManagers() {
    ArrayList<Employee> managers=new ArrayList<>();
    for(Employee e: credentialsDataAccessor.getEmployees()){
      if(e.getType()== EmployeeType.MANAGER){
        managers.add(e);
      }
    }
    return managers;
  }
  @Override
  public ArrayList<Employee> getWorkers() {
    ArrayList<Employee> workers=new ArrayList<>();
    for(Employee e: credentialsDataAccessor.getEmployees()){
      if(e.getType()== EmployeeType.WAREHOUSE_WORKER){
        workers.add(e);
      }
    }
    return workers;
  }




  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    support.firePropertyChange(evt.getPropertyName(),evt.getOldValue(),evt.getNewValue());
  }

  @Override
  public void addListener(String eventName, PropertyChangeListener listener) {
    support.addPropertyChangeListener(eventName,listener);
  }

  @Override
  public void removeListener(String eventName, PropertyChangeListener listener) {
    support.removePropertyChangeListener(eventName,listener);
  }
}
