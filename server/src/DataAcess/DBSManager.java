package DataAcess;

import Model.Product;
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
    password = "sara1900";
  }
  public DBSManager(String user, String password)
  {
    support = new PropertyChangeSupport(this);
    url = "jdbc:postgresql://localhost:5432/postgres";
    this.user = user;
    this.password = password;
  }

  /**
   * Method that creates a connection to the database
   * @return the connection
   */
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

  /**
   * Method that inserts a new user in the database
   * @param newUser the user to be inserted
   */
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
   * @return users as arrayList
   */
  @Override
  public ArrayList<User> getUsers() {

    String SQL = "SELECT * FROM " +SCHEMA+ ".users";
    ArrayList<User> users=new ArrayList<>();
    try (Connection conn = connect();
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
   * Get all products in the product-relation
   * @return arraylist of products corresponding to the index that is provided
   */
  @Override
  public ArrayList<Product> getProducts(int index) {
    System.out.println(index);
    String SQL = "SELECT * FROM " +SCHEMA+ ".products where products.product_id > "+ (index-1)*6 + " and products.product_id < "+(index*6+1);
    System.out.println(SQL);
    ArrayList<Product> products=new ArrayList<>();
    try (Connection conn = connect();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(SQL)) {
      System.out.println("All products:");
      while (rs.next())
      {
        products.add(new Product(rs.getString("product_name"), rs.getString("type"), Double.parseDouble(rs.getString("price")),
                rs.getString("description"),Integer.parseInt(rs.getString("amount"))));

      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    System.out.println(products);
    return products;
  }

  /**
   * Method that adds a product to the database
   * @param p the product to be added
   */
  @Override
  public void addProduct(Product p) {
    String SQL = "INSERT INTO " + "eshop.products(product_name,description,type,amount,price) " + "VALUES(?,?,?,?,?)";

    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS))
    {
      System.out.println(p);
      pstmt.setString(1, p.getName());
      pstmt.setString(2, p.getDescription());
      pstmt.setString(3, p.getType());
      pstmt.setInt(4, p.getQuantityP());
      pstmt.setDouble(5, p.getPrice());

      int affectedRows = pstmt.executeUpdate();
      if (affectedRows <= 0)
        throw new RuntimeException("Product insertion failed");

    }catch (SQLException ex) {
      System.out.println(ex.getMessage());
      throw new RuntimeException(ex.getMessage());
    }
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

  /** Method that checks if username exists
   *
   * @param username
   * @return true or false depending on what the database returns
   */
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
