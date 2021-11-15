package Model;

import DataAcess.DBSManager;
import DataAcess.DataAccessor;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ServerModelManager implements Model
{
  private DataAccessor dbsManager;
  private PropertyChangeSupport property;
  public ServerModelManager()
  {
    dbsManager = new DBSManager("postgres","sara1900");
    property = new PropertyChangeSupport(this);
  }

  @Override public int userCount()
  {
    return dbsManager.getUserCount();
  }

  @Override public void registerUser(User newUser)
  {
    if(dbsManager.getUsers().contains(newUser)){
      throw new IllegalArgumentException("The user already exists");
    }
    dbsManager.registerUser(newUser);
  }

  @Override public void loginUser(User loggingUser)
  {
    dbsManager.loginUser(loggingUser.getUsername(), loggingUser.getPassword(), loggingUser.getClientID());
  }

  @Override public void addListener(String eventName,
      PropertyChangeListener listener)
  {
    property.addPropertyChangeListener(eventName, listener);
  }

  @Override public void removeListener(String eventName,
      PropertyChangeListener listener)
  {
    property.removePropertyChangeListener(eventName, listener);
  }
}