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

public class DBSManager implements DataAccessor
{
  
  private Connection connection;
  private final String url;
  private final String user;
  private final String password;
  private PropertyChangeSupport support;
  private static final String SCHEMA = "eshop";

  public DBSManager()
  {
    support = new PropertyChangeSupport(this);

    url = "jdbc:postgresql://localhost:5432/postgres";
    user = "postgres";
    password = "4280";
  }
  public DBSManager(String user, String password)
  {
    support = new PropertyChangeSupport(this);
    url = "jdbc:postgresql://localhost:5432/postgres";
    this.user = user;
    this.password = password;
  }


  private Connection connect()
  {
    Connection conn = null;
    try {
      conn = DriverManager.getConnection(url, user, password);
      System.out.println("Connected to the PostgreSQL server successfully.");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return conn;
  }

  @Override public void registerUser(User newUser)
  {
    String SQL = "INSERT INTO " + "eshop.users(user_name,pass,email,first_name,last_name) " + "VALUES(?,?,?,?,?)";

    try (Connection conn = connect();
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
    try (Connection conn = connect();
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
   */
  public ArrayList<User> getUsers() {

    String SQL = "SELECT * FROM " +SCHEMA+ ".users";

    try (Connection conn = connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(SQL)) {
      System.out.println("All users:");
      // display actor information
      while (rs.next())
      {
        System.out.println(rs.getString("user_name") + "\t"
            + rs.getString("pass"));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    return null;
  }

  /**
   * Check login credentials
   * @return true if passwords matches; false if don't
   */
  private boolean checkPassword(String pass, String username) {
    String SQL = "SELECT pass FROM eshop.users WHERE user_name = " + "'"+username+"'";
    try (Connection conn = connect();
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
  private boolean checkUsername( String username) {
    String SQL = "SELECT user_name FROM eshop.users WHERE user_name = " + "'"+username+"'";
    try (Connection conn = connect();
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
