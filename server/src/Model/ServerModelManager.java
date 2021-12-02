package Model;

import DataAcess.*;

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

    credentialsDataAccessor.addListener("InvalidPassword",this);
    credentialsDataAccessor.addListener("InvalidUser",this);
    credentialsDataAccessor.addListener("SuccessfulLogin",this);
    productsDataAcessor.addListener("ProductReply",this);
  }



  @Override public int userCount()
  {
    return credentialsDataAccessor.getUserCount();
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

  @Override public void addNewOrder(Order newOrder)
  {
    ordersDataAccessor.addNewOrder(newOrder);
  }

  @Override public void registerUser(User newUser)
  {
    credentialsDataAccessor.registerUser(newUser);
  }

  @Override public User loginUser(String username, String password, String selectedUserType)
  {
    User tempUser = credentialsDataAccessor.loginUser(username,password, selectedUserType);
    tempUser.setOrders(ordersDataAccessor.getOrders(tempUser.getUserId()));
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
