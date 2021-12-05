package DataAcess;

import Model.Customer;
import Model.Employee;

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





  public void registerEmployee(Employee newEmployee)
  {
    String SQL = "INSERT INTO " +SCHEMA+".employees" +"(first_name,last_name, pin, employee_type) " + "VALUES(?,?,?,?)";

    try (Connection conn = DBSConnection.getInstance().connect();
        PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS))
    {

      pstmt.setString(1, newEmployee.getFirstName());
      pstmt.setString(2, newEmployee.getLastName());
      pstmt.setInt(3, newEmployee.getPin());
      pstmt.setString(4, newEmployee.getType().toString());

      int affectedRows = pstmt.executeUpdate();
      if (affectedRows <= 0)
        throw new RuntimeException("Register failed");
      if(newEmployee.getType()== Employee.EmployeeType.MANAGER) support.firePropertyChange("AdminReply",null,getManagers());
    }catch (SQLException ex) {
      System.out.println(ex.getMessage());
      throw new RuntimeException(ex.getMessage());
    }
  }

  public void registerCustomer(Customer newCustomer)
  {
    String SQL = "INSERT INTO " +SCHEMA+".customers" +"(user_name, pass, email, first_name, last_name) " + "VALUES(?,?,?,?,?)";

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
  public Customer loginCustomer(String username, String password)
  {
    Customer loggedCustomer = null;

    String table = "customers";
    String SQL = "SELECT * FROM " +SCHEMA+"."+table+ " WHERE user_name = " + "'"+username+"'";
    try (Connection conn =  DBSConnection.getInstance().connect();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(SQL))
    {
      rs.next();
      if (rs.getRow()==1 && rs.getString(2).equals(username) && rs.getString(3).equals(password)){
        loggedCustomer = new Customer(rs.getString(1), rs.getString(2),
                rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt("customer_id"));
      }
      else {
        throw new RuntimeException("Wrong credentials - access denied");
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return loggedCustomer;
  }
  @Override
  public Employee loginEmployee(int ID, int pin)
  {
    Employee loggedEmployee = null;

    String table = "employees";
    String SQL = "SELECT * FROM " +SCHEMA+"."+table+ " WHERE employee_id = " + "'"+ID+"'";
    try (Connection conn =  DBSConnection.getInstance().connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(SQL))
    {
      rs.next();
      Employee.EmployeeType type=null;
      if (rs.getInt(1)==ID && rs.getInt(4)==pin) {
        if (rs.getString(5).equalsIgnoreCase("Admin"))
          type= Employee.EmployeeType.ADMIN;
        else if (rs.getString(5).equalsIgnoreCase("Manager")) type= Employee.EmployeeType.MANAGER;
        else if (rs.getString(5).equalsIgnoreCase("Worker")) type= Employee.EmployeeType.WORKER;
          loggedEmployee = new Employee(rs.getString(2), rs.getString(3),
                  rs.getInt(4), type, rs.getInt(1));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    return loggedEmployee;
  }

  @Override
  public void removeEmployee(Employee e) {
    String SQL = "DELETE FROM " +SCHEMA+ ".employees WHERE " +SCHEMA+ ".employees"+ ".employee_id = '" +e.getID()+ "'";

    try (Connection conn = DBSConnection.getInstance().connect();
         Statement stmt = conn.createStatement())
    {
      int affectedRows = stmt.executeUpdate(SQL);
      if (affectedRows <= 0)
        throw new RuntimeException("Employee deletion failed");
      if(e.getType()== Employee.EmployeeType.MANAGER) support.firePropertyChange("AdminReply",null,getManagers());
    }catch (SQLException ex) {
      System.out.println(ex.getMessage());
      throw new RuntimeException(ex.getMessage());
    }
  }

  private ArrayList<Employee> getManagers() {
    String SQL = "SELECT * FROM " +SCHEMA+ ".employees WHERE employee_type=" +"'"+"MANAGER"+"'";
    ArrayList<Employee> employees=new ArrayList<>();
    try (Connection conn =  DBSConnection.getInstance().connect();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(SQL)) {
      System.out.println("All users:");
      // display actor information
      Employee.EmployeeType type=null;
      while (rs.next())
      {
        if (rs.getString("employee_type").equalsIgnoreCase("Admin")) type= Employee.EmployeeType.ADMIN;
        else if (rs.getString("employee_type").equalsIgnoreCase("Manager")) type= Employee.EmployeeType.MANAGER;
        else if (rs.getString("employee_type").equalsIgnoreCase("Worker")) type= Employee.EmployeeType.WORKER;
        employees.add(new Employee(rs.getString("first_name"),rs.getString("last_name"),
                rs.getInt("pin"), type,rs.getInt("employee_id")));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    return employees;
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
  public ArrayList<Employee> getEmployees() {

    String SQL = "SELECT * FROM " +SCHEMA+ ".employees";
    ArrayList<Employee> employees=new ArrayList<>();
    try (Connection conn =  DBSConnection.getInstance().connect();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(SQL)) {
      System.out.println("All users:");
      // display actor information
      Employee.EmployeeType type=null;
      while (rs.next())
      {
        if (rs.getString("employee_type").equalsIgnoreCase("Admin")) type= Employee.EmployeeType.ADMIN;
        else if (rs.getString("employee_type").equalsIgnoreCase("Manager")) type= Employee.EmployeeType.MANAGER;
        else if (rs.getString("employee_type").equalsIgnoreCase("Worker")) type= Employee.EmployeeType.WORKER;
        employees.add(new Employee(rs.getString("first_name"),rs.getString("last_name"),
                rs.getInt("pin"), type,rs.getInt("employee_id")));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    return employees;
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
