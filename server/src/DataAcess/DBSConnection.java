package DataAcess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.locks.ReentrantLock;

/*
To query data from a table using JDBC, you use the following steps:
  1. Establish a database connection to the PostgreSQL server.
  2. Create an instance of the Statement object
  3. Execute a statement to get a ResultSet object
  4. Process the ResultSet object.
  5. Close the database connection.

https://www.postgresqltutorial.com/postgresql-jdbc/query/
 */

/**
 * Class that represents a global access point to the database [Singleton pattern].
 * Therefore, it is a shared object/resource, thus taking role of a monitor class
 */
public class DBSConnection{

    private static DBSConnection connection;
    private final String url;
    private final String user;
    private final String password;
    private final static ReentrantLock lock = new ReentrantLock();

    private DBSConnection(String url, String user, String password){
        this.url = url;
        this.user = user;
        this.password = password;
    }

    static DBSConnection getInstance(){
        if(connection==null){
            synchronized (lock){
                if(connection==null){
                        connection= new DBSConnection("jdbc:postgresql://localhost:5432/postgres","postgres","4280");
                }
            }
        }
        return connection;
    }

    /**
     * Method responsible for establishing connection with the database
     * @return Connection object if successful else null
     */
    public Connection connect(){
        try {
            return DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
