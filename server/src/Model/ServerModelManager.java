package Model;

import DataAcess.DBSManager;
import DataAcess.DataAccessor;

public class ServerModelManager implements Model
{
  private DataAccessor dbsManager;
  public ServerModelManager()
  {
    dbsManager = new DBSManager("postgres","sara1900");
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

  @Override public void loginUser(User loggingUser)
  {
    dbsManager.loginUser(loggingUser);
  }


}
