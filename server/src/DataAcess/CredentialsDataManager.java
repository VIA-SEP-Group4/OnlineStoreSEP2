package DataAcess;

import Model.User;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.*;
import java.util.ArrayList;

/**
 * Class responsible for managing and accessing database tables/data related to users' credentials
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
    if (newUser.getUserType().equals(User.UserType.CUSTOMER))
      registerNewCustomer(newUser, "customers");

    else
      registerNewEmployee(newUser, "employees");
  }

  private void registerNewEmployee(User newEmployee, String table)
  {
    String SQL = "INSERT INTO " +SCHEMA+"."+table+"(first_name,last_name, pin, employee_type) " + "VALUES(?,?,?,?)";

    try (Connection conn = DBSConnection.getInstance().connect();
        PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS))
    {

      pstmt.setString(1, newEmployee.getFirstName());
      pstmt.setString(2, newEmployee.getLastName());
      pstmt.setString(3, newEmployee.getPassword());
      pstmt.setString(4, newEmployee.getUserType().toString());

      int affectedRows = pstmt.executeUpdate();
      if (affectedRows <= 0)
        throw new RuntimeException("Register failed");

    }catch (SQLException ex) {
      System.out.println(ex.getMessage());
      throw new RuntimeException(ex.getMessage());
    }
  }

  private void registerNewCustomer(User newCustomer, String table)
  {
    String SQL = "INSERT INTO " +SCHEMA+"."+table+"(user_name, pass, email, first_name, last_name) " + "VALUES(?,?,?,?,?)";

    try (Connection conn = DBSConnection.getInstance().connect();
        PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS))
    {

      pstmt.setString(1, newCustomer.getUsername());
      pstmt.setString(2, newCustomer.getPassword());
      pstmt.setString(3, newCustomer.getEmail());
      pstmt.setString(4, newCustomer.getFirstName());
      pstmt.setString(5, newCustomer.getLastName());

      int affectedRows = pstmt.executeUpdate();
      if (affectedRows <= 0)
        throw new RuntimeException("Register failed");

      System.out.println("customer-" + newCustomer + " registered");
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
  @Override public User loginUser(String username, String password, String selectedUserType)
  {
    User loggedUser;

    if (selectedUserType.equals("customer"))
      loggedUser = loginCustomer(username, password);

    else
      loggedUser = loginEmployee(username, password);

    return loggedUser;
  }

  //TODO ... does NOT fit USER-constructor
  private User loginEmployee(String firstName, String pin)
  {
    User loggedEmployee = null;

    String table = "employees";
    String SQL = "SELECT * FROM " +SCHEMA+"."+table+ " WHERE first_name = " + "'"+firstName+"'";
    try (Connection conn =  DBSConnection.getInstance().connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(SQL))
    {
      rs.next();
      if (rs.getString(1).equals(firstName) && rs.getString(2).equals(pin))
        loggedEmployee = new User(rs.getString(1), rs.getString(2),
            rs.getString(3), rs.getString(4), rs.getString(5));
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return loggedEmployee;
  }

  private User loginCustomer(String username, String password)
  {
    User loggedCustomer = null;

    String table = "customers";
    String SQL = "SELECT * FROM " +SCHEMA+"."+table+ " WHERE user_name = " + "'"+username+"'";
    try (Connection conn =  DBSConnection.getInstance().connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(SQL))
    {
      rs.next();
//      System.out.println(rs.getRow());
      if (rs.getRow()==1 && rs.getString(2).equals(username) && rs.getString(3).equals(password))
        loggedCustomer = new User(rs.getString(1), rs.getString(2),
            rs.getString(3), rs.getString(4), rs.getString(5));
      else
        throw new RuntimeException("Wrong credentials - access denied");
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return loggedCustomer;
  }

  /**
   * Check login credentials
   * @return true if passwords matches; false if don't
   */
  private boolean checkPassword(String pass, String username, String table) {
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
  private boolean checkUsername( String username, String table) {
    String SQL = "SELECT user_name FROM " +SCHEMA+"."+table+ " WHERE user_name = " + "'"+username+"'";
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






  @Override
  public void addListener( String eventName,PropertyChangeListener listener) {
    support.addPropertyChangeListener(eventName,listener);
  }

  @Override
  public void removeListener(String eventName, PropertyChangeListener listener) {
    support.removePropertyChangeListener(eventName,listener);
  }
}
