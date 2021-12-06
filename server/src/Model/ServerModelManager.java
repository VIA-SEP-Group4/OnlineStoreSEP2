package Model;

import DataAcess.*;
import Enums.EmployeeType;
import Model.Models.Customer;
import Model.Models.Employee;
import Model.Models.Order;
import Model.Models.Product;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class ServerModelManager implements Model, PropertyChangeListener
{
  private CredentialsDataAccessor credentialsDataAccessor;
  private ProductsDataAcessor productsDataAcessor;
  private OrdersDataAccessor ordersDataAccessor;

  private PropertyChangeSupport support;
  public ServerModelManager()
  {
    support=new PropertyChangeSupport(this);

    credentialsDataAccessor = new CredentialsDataManager();
    productsDataAcessor=new ProductsDataManager();
    ordersDataAccessor = new OrdersDataManager();

    credentialsDataAccessor.addListener("AdminReply",this);
    credentialsDataAccessor.addListener("ManagerReply",this);
    productsDataAcessor.addListener("ProductReply",this);
  }



  @Override public int userCount()
  {
    return credentialsDataAccessor.getUserCount();
  }

  @Override
  public Employee loginEmployee(int ID, int pin) {
    return credentialsDataAccessor.loginEmployee(ID,pin);
  }

  @Override
  public ArrayList<Product> getProducts() {
    return productsDataAcessor.getProducts();
  }


  @Override
  public void addProduct(Product p) {
    productsDataAcessor.addProduct(p);
  }

  @Override public void deleteProduct(Product p)
  {
    productsDataAcessor.deleteProduct(p);
  }

  @Override
  public void removeEmployee(Employee e) {
    credentialsDataAccessor.removeEmployee(e);
  }

  @Override public void addNewOrder(Order newOrder)
  {
    ordersDataAccessor.addNewOrder(newOrder);
  }

  @Override public ArrayList<Order> getOrders(int customerId)
  {
    return ordersDataAccessor.getOrders(customerId);
  }

  @Override public void updateStock(Product p, int desiredQuantity)
  {
    productsDataAcessor.updateStock(p, desiredQuantity);
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

  @Override public void registerCustomer(Customer newUser)
  {
    credentialsDataAccessor.registerCustomer(newUser);
  }

  @Override
  public void registerEmployee(Employee employee) {
    credentialsDataAccessor.registerEmployee(employee);
  }

  @Override public Customer loginCustomer(String username, String password)
  {
    Customer tempUser = credentialsDataAccessor.loginCustomer(username,password);
    tempUser.setOrders(ordersDataAccessor.getOrders(tempUser.getCustomerId()));
    return tempUser;
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
