package Model;

import Networking.Client;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class LoginModelManager implements LoginModel, PropertyChangeListener
{

  private Client client;
  private PropertyChangeSupport support;
  private ArrayList<Product> basket;

  public LoginModelManager(Client client)
  {
    support=new PropertyChangeSupport(this);
    this.client = client;
    client.startClient();
    addProduct(new Product("Fanta","Non-Alcoholic Beverage",14.2,"Great Fake Orange Juice",10));
    addProduct(new Product("Whisky","Alcoholic Beverage",25.2,"Kept in Oak Barrels",11));
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

  @Override public ArrayList<Product> getProducts(int index)
  {
    return client.getProducts(index);
  }

  @Override public void addBasket(Product product)
  {
    basket.add(product);

  }

  @Override public ArrayList<Product> getBasket()
  {
    return basket;
  }

  @Override
  public void addProduct(Product p) {
    try {
      client.addProduct(p);
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }

  @Override public String getId()
  {
    try
    {
      return client.getID();
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    } return "";
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
