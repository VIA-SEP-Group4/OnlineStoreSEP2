package DataAcess;

import Model.User;

import java.sql.*;

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

  private static final String SCHEMA = "eshop";

  public DBSManager()
  {
    url = "jdbc:postgresql://localhost:5432/postgres";
    user = "postgres";
    password = "4280";
  }
  public DBSManager(String user, String password)
  {
    url = "jdbc:postgresql://localhost:5432/postgres";
    this.user = user;
    this.password = password;
  }


  private Connection connect()
  {
    Connection conn = null;
    try {
      conn = DriverManager.getConnection(url, user, password);
      conn.setAutoCommit(false);
      System.out.println("Connected to the PostgreSQL server successfully.");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return conn;
  }

  @Override public void registerUser(User newUser)
  {
    String SQL = "INSERT INTO " +SCHEMA+ ".users(username,pass) " + "VALUES(?,?)";
    long id = 0;
    try (Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS))
    {
      pstmt.setString(1, newUser.getUsernameString());
      pstmt.setString(2, newUser.getPasswordString());

      System.out.println(pstmt);
      pstmt.executeUpdate();

      try (ResultSet rs = pstmt.getGeneratedKeys()){
        if (rs.next()) {
          System.out.println(rs.getString("username") + "\t"
              + rs.getString("pass"));
        }
      }catch (SQLException ex) {
        System.out.println(ex.getMessage());
      }

//      int affectedRows = pstmt.executeUpdate();
//      if (affectedRows > 0)
//      {
//        try (ResultSet rs = pstmt.getGeneratedKeys()){
//          if (rs.next()) {
//            System.out.println(rs);
//            id = rs.getLong(1);
//
//            System.out.println(rs.getString("username") + "\t"
//                + rs.getString("pass"));
//          }
//        }catch (SQLException ex) {
//          System.out.println(ex.getMessage());
//        }
//      }
//      else
//      {
//        throw new RuntimeException("denied");
//      }

    }catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }

  @Override public void loginUser(User loggingUser)
  {

  }

  /**
   * Get user count
   * @return number of users stored in the user-relation
   */
  @Override public int getUserCount() {
    int count = 0;

//    String SQL = "SELECT count(*) FROM users";
//    Connection conn = connect();
//    try
//    {
//      Statement stmt = conn.createStatement();
//      stmt.execute("SET search_path = 'eshop'");
//      ResultSet rs = stmt.executeQuery(SQL);
//
//      rs.next();
//      count = rs.getInt(1);
//
//      rs.close();
//      stmt.close();
//      conn.close();
//    }
//    catch (SQLException e)
//    {
//      System.out.println(e.getMessage());
//    }

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
  public void getUsers() {

    String SQL = "SELECT * FROM " +SCHEMA+ ".users";

    try (Connection conn = connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(SQL)) {
      System.out.println("All users:");
      // display actor information
      while (rs.next())
      {
        System.out.println(rs.getString("username") + "\t"
            + rs.getString("pass"));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }

  /**
   * Check login credentials
   * @return true if passwords matches; false if don't
   */
  public boolean checkLoginCredentials(String username, String password) {
    String SQL = "SELECT password FROM user WHERE username = " + username;

    try (Connection conn = connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(SQL))
    {
      rs.next();
      return rs.getString(2).equals(password);
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return false;
  }

}
