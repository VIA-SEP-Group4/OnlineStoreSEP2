package Model;

import DataAcess.DBSManager;
import DataAcess.DataAccessor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ServerModelManager implements Model, PropertyChangeListener
{
  private DataAccessor dbsManager;
  private PropertyChangeSupport support;
  public ServerModelManager()
  {
    support=new PropertyChangeSupport(this);
    dbsManager = new DBSManager("postgres","sara1900");
    dbsManager.addListener("InvalidPassword",this);
    dbsManager.addListener("InvalidUser",this);
    dbsManager.addListener("SuccessfulLogin",this);

  }


  @Override public String toUpperCase(String text)
  {
    return text.toUpperCase();
  }


  @Override public int userCount()
  {
    return dbsManager.getUserCount();
  }

  @Override public void registerUser(User newUser)
  {
    dbsManager.registerUser(newUser);
  }

  @Override public void loginUser(String username, String password,String clientID)
  {
    dbsManager.loginUser(username,password,clientID);
  }


  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    support.firePropertyChange("Validation",evt.getOldValue(),evt.getNewValue());
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
