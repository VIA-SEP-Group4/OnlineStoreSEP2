package DataAcess;

import Model.User;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.*;
import java.util.ArrayList;

/*
To query data from a table using JDBC, you use the following steps:
  1. Establish a database connection to the PostgreSQL server.
  2. Create an instance of the Statement object
  3. Execute a statement to get a ResultSet object
  4. Process the ResultSet object.
  5. Close the database connection.

https://www.postgresqltutorial.com/postgresql-jdbc/query/
 */

public class CredentialsDataManager implements CredentialsDataAccessor
{
  private PropertyChangeSupport support;
  private static final String SCHEMA = "eshop";

  public CredentialsDataManager() {
    support = new PropertyChangeSupport(this);

  }



  /**
   * Method that inserts a new user in the database
   * @param newUser the user to be inserted
   */
  @Override public void registerUser(User newUser)
  {
    String SQL = "INSERT INTO " + "eshop.users(user_name,pass,email,first_name,last_name) " + "VALUES(?,?,?,?,?)";

    try (Connection conn = DBSConnection.getInstance().connect();
         PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS))
    {
      System.out.println(newUser);

      pstmt.setString(1, newUser.getUsername());
      pstmt.setString(2, newUser.getPassword());
      pstmt.setString(3, newUser.getEmail());
      pstmt.setString(4, newUser.getFirstName());
      pstmt.setString(5, newUser.getLastName());

      int affectedRows = pstmt.executeUpdate();
      if (affectedRows <= 0)
        throw new RuntimeException("Register failed");

    }catch (SQLException ex) {
      System.out.println(ex.getMessage());
      throw new RuntimeException(ex.getMessage());
    }
  }

  /**
   * Method that checks if a user exists and whether the password matches
   * @param username Username that the user logs in with
   * @param password Password that the user logs in with
   */
  @Override public void loginUser(String username, String password)
  {
    if(!checkUsername(username))
      throw new RuntimeException("User doesn't exist");
    else if(!checkPassword(password,username))
      throw new RuntimeException("Password doesn't match");
  }

  /**
   * Get user count
   * @return number of users stored in the user-relation
   */
  @Override public int getUserCount() {
    int count = 0;

    String SQL = "SELECT count(*) FROM " +SCHEMA+ ".users";
    try (Connection conn = DBSConnection.getInstance().connect();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(SQL))
    {
      rs.next();
      count = rs.getInt(1);
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return count;
  }

  /**
   * Get all rows in the users-relation
   * @return users as arrayList
   */
  @Override
  public ArrayList<User> getUsers() {

    String SQL = "SELECT * FROM " +SCHEMA+ ".users";
    ArrayList<User> users=new ArrayList<>();
    try (Connection conn =  DBSConnection.getInstance().connect();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(SQL)) {
      System.out.println("All users:");
      // display actor information
      while (rs.next())
      {
        System.out.println(rs.getString("user_name") + "\t"
            + rs.getString("pass"));
        users.add(new User(rs.getString("user_name"),rs.getString("pass"),
                rs.getString("email"),rs.getString("first_name"),rs.getString("last_name")));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    return users;
  }




  /**
   * Check login credentials
   * @return true if passwords matches; false if don't
   */
  private boolean checkPassword(String pass, String username) {
    String SQL = "SELECT pass FROM eshop.users WHERE user_name = " + "'"+username+"'";
    try (Connection conn =  DBSConnection.getInstance().connect();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(SQL))
    {
      rs.next();
      System.out.println(rs.getString(1));
      return rs.getString(1).equals(pass);
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return false;
  }

  /** Method that checks if username exists
   *
   * @param username
   * @return true or false depending on what the database returns
   */
  private boolean checkUsername( String username) {
    String SQL = "SELECT user_name FROM eshop.users WHERE user_name = " + "'"+username+"'";
    try (Connection conn =  DBSConnection.getInstance().connect();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(SQL))
    {
      rs.next();
      return rs.getString(1).equals(username);
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return false;
  }

  @Override
  public void addListener( String eventName,PropertyChangeListener listener) {
    support.addPropertyChangeListener(eventName,listener);
  }

  @Override
  public void removeListener(String eventName, PropertyChangeListener listener) {
    support.removePropertyChangeListener(eventName,listener);
  }
}
