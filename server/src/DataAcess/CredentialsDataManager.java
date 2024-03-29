package DataAcess;

import Enums.EmployeeType;
import Model.Models.Customer;
import Model.Models.Employee;
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





  public  void  registerEmployee(Employee newEmployee)
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


    }catch (SQLException ex) {
      System.out.println(ex.getMessage());
      throw new RuntimeException(ex.getMessage());
    }
    String SQL2 = "Select employee_id FROM " +SCHEMA+".employees"+ " WHERE first_name="+"'"+newEmployee.getFirstName()+"'"+"and last_name="+"'"+newEmployee.getLastName()+"'";
    try (Connection conn = DBSConnection.getInstance().connect();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(SQL2))
    {
      rs.next();
      int newEmployeeID = rs.getInt("employee_id");
      newEmployee.setEmployeeId(newEmployeeID);
    }
    catch (SQLException ex) {
      System.out.println(ex.getMessage());
      throw new RuntimeException(ex.getMessage());
    }
    if(newEmployee.getType()== EmployeeType.MANAGER) support.firePropertyChange("AdminReply",null,newEmployee);
    else if(newEmployee.getType()==EmployeeType.WAREHOUSE_WORKER) support.firePropertyChange("ManagerReply",null,newEmployee);
  }

  public  void registerCustomer(Customer newCustomer)
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
        loggedCustomer = new Customer(
            rs.getString("user_name"),
            rs.getString("pass"),
            rs.getString("email"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getInt("customer_id"));
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
  public  Employee loginEmployee(int ID, int pin)
  {
    Employee loggedEmployee = null;

    String table = "employees";
    String SQL = "SELECT * FROM " +SCHEMA+"."+table+ " WHERE employee_id = " + "'"+ID+"'";
    try (Connection conn =  DBSConnection.getInstance().connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(SQL))
    {
      rs.next();
      EmployeeType type=null;
      if (rs.getInt(1)==ID && rs.getInt(4)==pin) {
        if (rs.getString(5).equalsIgnoreCase("Admin"))
          type= EmployeeType.ADMIN;
        else if (rs.getString(5).equalsIgnoreCase("Manager"))
          type= EmployeeType.MANAGER;
        else if (rs.getString(5).equalsIgnoreCase("warehouse_worker"))
          type= EmployeeType.WAREHOUSE_WORKER;

          loggedEmployee = new Employee(rs.getString(2), rs.getString(3),
                  rs.getInt(4), type, rs.getInt(1));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    return loggedEmployee;
  }
 //TODO handle exception by sending string back
  @Override
  public  void removeEmployee(Employee e) {
    String SQL = "DELETE FROM " +SCHEMA+ ".employees WHERE employee_id = '"+e.getID()+"'";

    try (Connection conn = DBSConnection.getInstance().connect();
         Statement stmt = conn.createStatement())
    {
      int affectedRows = stmt.executeUpdate(SQL);
      if (affectedRows <= 0)
        throw new RuntimeException("Employee deletion failed");
    }catch (SQLException ex) {
      System.out.println(ex.getMessage());
      throw new RuntimeException(ex.getMessage());
    }
    if(e.getType()== EmployeeType.MANAGER) support.firePropertyChange("AdminReplyDelete",null,e);
    else if(e.getType()==EmployeeType.WAREHOUSE_WORKER) support.firePropertyChange("ManagerReplyDelete",null,e);
  }

  @Override public  void deleteCustomer(int customerId)
  {
    String table = "customers";
    String SQL = "DELETE FROM " +SCHEMA+"."+table+ " WHERE customer_id = '"+customerId+"'";

    try (Connection conn = DBSConnection.getInstance().connect();
        Statement stmt = conn.createStatement())
    {
      int affectedRows = stmt.executeUpdate(SQL);
      if (affectedRows <= 0)
        throw new RuntimeException("Customer deletion failed");
    }catch (SQLException ex) {
      System.out.println(ex.getMessage());
      throw new RuntimeException(ex.getMessage());
    }
  }

  @Override public  void editCustomer(Customer editedCustomer)
  {
    String table = "customers";
    String SQL = "UPDATE " +SCHEMA+"."+table+ " set user_name='"+editedCustomer.getUsername()+"'" + ", first_name="+"'"+editedCustomer.getFirstName()+"'"+", last_name="+"'"+editedCustomer.getLastName()+"'"+",  pass ='"+editedCustomer.getPassword()+ "'"+ ", email ='"+editedCustomer.getEmail()+"'" +" where customer_id="+editedCustomer.getCustomerId();

    try (Connection conn = DBSConnection.getInstance().connect();
        Statement stmt = conn.createStatement())
    {
      int affectedRows = stmt.executeUpdate(SQL);
      if (affectedRows <= 0)
        throw new RuntimeException("Customer update failed");
    }catch (SQLException ex) {
      System.out.println(ex.getMessage());
      throw new RuntimeException(ex.getMessage());
    }
  }

  private  ArrayList<Employee> getManagers() {
    String SQL = "SELECT * FROM " +SCHEMA+ ".employees WHERE employee_type=" +"'"+"MANAGER"+"'";
    ArrayList<Employee> employees=new ArrayList<>();
    try (Connection conn =  DBSConnection.getInstance().connect();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(SQL)) {
      System.out.println("All users:");
      // display actor information
      EmployeeType type=null;
      while (rs.next())
      {

        employees.add(new Employee(rs.getString("first_name"),rs.getString("last_name"),
                rs.getInt("pin"), EmployeeType.MANAGER,rs.getInt("employee_id")));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    return employees;
  }
  private  ArrayList<Employee> getWorkers() {
    String SQL = "SELECT * FROM " +SCHEMA+ ".employees WHERE employee_type=" +"'"+"WAREHOUSE_WORKER"+"'";
    ArrayList<Employee> employees=new ArrayList<>();
    try (Connection conn =  DBSConnection.getInstance().connect();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(SQL)) {
      System.out.println("All users:");
      // display actor information
      EmployeeType type=null;
      while (rs.next())
      {

        employees.add(new Employee(rs.getString("first_name"),rs.getString("last_name"),
                rs.getInt("pin"), EmployeeType.WAREHOUSE_WORKER,rs.getInt("employee_id")));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    return employees;
  }




  /** Method that checks if username exists
   *
   * @param username
   * @return true or false depending on what the database returns
   */
  private  boolean checkUsername( String username, String table) {
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
   * Get all rows in the users-relation
   * @return users as arrayList
   */
  @Override
  public  ArrayList<Employee> getEmployees() {

    String SQL = "SELECT * FROM " +SCHEMA+ ".employees";
    ArrayList<Employee> employees=new ArrayList<>();
    try (Connection conn =  DBSConnection.getInstance().connect();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(SQL)) {
      System.out.println("All users:");
      // display actor information
      EmployeeType type=null;
      while (rs.next())
      {
        if (rs.getString("employee_type").equalsIgnoreCase("Admin")) type= EmployeeType.ADMIN;
        else if (rs.getString("employee_type").equalsIgnoreCase("Manager")) type= EmployeeType.MANAGER;
        else if (rs.getString("employee_type").equalsIgnoreCase("Warehouse_Worker")) type= EmployeeType.WAREHOUSE_WORKER;
        employees.add(new Employee(rs.getString("first_name"),rs.getString("last_name"),
                rs.getInt("pin"), type,rs.getInt("employee_id")));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    return employees;
  }


  @Override
  public  void editEmployee(Employee e) {
    String SQL = "update eshop.employees set first_name="+"'"+e.getFirstName()+"'"+", last_name="+"'"+e.getLastName()+"'"+",  pin ="+e.getPin()+ " where employee_id= '"+e.getID()+"'";

    try (Connection conn = DBSConnection.getInstance().connect();
         Statement stmt = conn.createStatement())
    {
      int affectedRows = stmt.executeUpdate(SQL);
      System.out.println(affectedRows);
      if (affectedRows <= 0)
        throw new RuntimeException("Employee update failed");
      if(e.getType()== EmployeeType.MANAGER) support.firePropertyChange("AdminReply",null,e);
      else if(e.getType()==EmployeeType.WAREHOUSE_WORKER) support.firePropertyChange("ManagerReply",null,e);
    }catch (SQLException ex) {
      System.out.println(ex.getMessage());
      throw new RuntimeException(ex.getMessage());
    }
  }
  @Override
  public  void addListener( String eventName,PropertyChangeListener listener) {
    support.addPropertyChangeListener(eventName,listener);
  }

  @Override
  public void removeListener(String eventName, PropertyChangeListener listener) {
    support.removePropertyChangeListener(eventName,listener);
  }
}
