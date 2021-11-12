package DataAcess;

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

public class DBSManager {
  
  private Connection connection;
  private final String url;
  private final String user;
  private final String password;
  private String schemaName;

  public DBSManager(String schemaName)
  {
    this.schemaName = schemaName;

    url = "jdbc:postgresql://localhost:5432/postgres";
    user = "postgres";
    password = "4280";
  }
  public DBSManager(String schemaName, String user, String password)
  {
    this.schemaName = schemaName;

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
      System.out.println("Opened database successfully");

      Statement stmt = conn.createStatement();
      stmt.execute("set search_path to \"users\"");

      System.out.println("Connected to the PostgreSQL server successfully.");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return conn;
  }


  /**
   * Get user count
   * @return number of users stored in the user-relation
   */
  public int getUserCount() {
    String SQL = "SELECT count(*) FROM users";
    int count = 0;

    Connection conn = connect();
    try
    {
      Statement stmt = conn.createStatement();
      stmt.execute("SET search_path = 'eshop'");
      ResultSet rs = stmt.executeQuery(SQL);

      rs.next();
      count = rs.getInt(1);

      rs.close();
      stmt.close();
      conn.close();
    }
    catch (SQLException e)
    {
      System.out.println(e.getMessage());
    }

    return count;
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
