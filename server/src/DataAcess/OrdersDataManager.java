package DataAcess;

import Model.Models.Order;
import Model.Models.Product;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.*;
import java.util.ArrayList;

public class OrdersDataManager implements OrdersDataAccessor
{
  private static final String SCHEMA = "eshop";
  private static final String TABLE = "orders";
  private static final String TABLE2 = "ordered_products";

  private PropertyChangeSupport support;

  public OrdersDataManager()
  {
    support = new PropertyChangeSupport(this);
  }

  @Override public  void addNewOrder(Order newOrder)
  {
    String SQL;
    int affectedRows;

    try (Connection conn = DBSConnection.getInstance().connect())
    {
      SQL = "INSERT INTO " +SCHEMA+ "." +TABLE+ "(customer_id, timestamp) " + "VALUES(?,?)";
      PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
      pstmt.setInt(1, newOrder.getCustomerId());
      pstmt.setTimestamp(2, newOrder.getTimestampSQL());

      affectedRows = pstmt.executeUpdate();
      if (affectedRows <= 0)
        throw new RuntimeException("Order insertion failed");
      pstmt.close();

      //get and set ID of newly-created order:
      SQL = "Select order_id FROM " +SCHEMA+"."+TABLE+ " WHERE customer_id="+newOrder.getCustomerId()+ " AND " + "timestamp='"+newOrder.getTimestampSQL()+"'";
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(SQL);
      rs.next();
      int newOrderId = rs.getInt("order_id");
      newOrder.setOrderId(newOrderId);

      stmt.close();
      rs.close();

      //update ordered_products table accordingly
      SQL = "INSERT INTO " +SCHEMA+ "." +TABLE2+ "(product_id, order_id, quantity) " + "VALUES(?,?,?)";
      pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
      for (Product p : newOrder.getProducts())
      {
        pstmt.setInt(1, p.getProductId());
        pstmt.setInt(2, newOrder.getOrderId());
        pstmt.setInt(3, p.getQuantity());

        affectedRows = pstmt.executeUpdate();
        if (affectedRows <= 0)
          throw new RuntimeException("Product insertion failed");
      }
      pstmt.close();
    }
    catch (SQLException ex)
    {
      System.out.println(ex.getMessage());
      throw new RuntimeException(ex.getMessage());
    }

    //notify
    support.firePropertyChange("newOrder",null,newOrder);
  }

  @Override public  ArrayList<Order> getAllOrders()
  {
    String SQL = "SELECT * FROM " +SCHEMA+ "." +TABLE;
    ArrayList<Order> orders = new ArrayList<>();

    try (Connection conn =  DBSConnection.getInstance().connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(SQL))
    {
      //for each order
      while (rs.next())
      {
        int currOrderId = rs.getInt("order_id");
        String productsSQL = "select * from eshop.ordered_products o_p join eshop.products prod on o_p.product_id=prod.product_id WHERE order_id=" +currOrderId;

        Statement productsStmt = conn.createStatement();
        ResultSet productsRs = productsStmt.executeQuery(productsSQL);
        ArrayList<Product> currProducts = new ArrayList<>();
        //for each product
        while (productsRs.next())
        {
          currProducts.add(new Product(
              productsRs.getString("product_name"),
              productsRs.getString("type"),
              productsRs.getDouble("price"),
              productsRs.getString("description"),
              productsRs.getInt("quantity"),
              productsRs.getInt("product_id")
              )
          );
        }
        productsRs.close();

        orders.add(new Order(
            rs.getInt("order_id"),
            rs.getInt("customer_id"),
            rs.getInt("warehouse_worker_id"),
            rs.getString("status"),
            rs.getTimestamp("timestamp"),
            currProducts)
        );
      }

    } catch (SQLException ex){
      System.out.println(ex.getMessage());
    }

    return orders;
  }

  @Override public  ArrayList<Order> getOrders(int customerId)
  {
    //fetch order's products first:

    String SQL = "SELECT * FROM " +SCHEMA+ "." +TABLE + " WHERE customer_id=" +customerId;
    ArrayList<Order> orders = new ArrayList<>();

    try (Connection conn =  DBSConnection.getInstance().connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(SQL))
    {

      //for each order
      while (rs.next())
      {
        int currOrderId = rs.getInt("order_id");
        ArrayList<Product> currProducts = getOrderProducts(currOrderId);

        orders.add(new Order(
            rs.getInt("order_id"),
            rs.getInt("customer_id"),
            rs.getInt("warehouse_worker_id"),
            rs.getString("status"),
            rs.getTimestamp("timestamp"),
            currProducts)
        );
      }

    } catch (SQLException ex){
      System.out.println(ex.getMessage());
    }

    return orders;
  }

  @Override public  ArrayList<Order> getOrders(String orderStatus)
  {
    //fetch order's products first:

    String SQL = "SELECT * FROM " +SCHEMA+ "." +TABLE + " WHERE status = '" +orderStatus + "'";
    ArrayList<Order> orders = new ArrayList<>();

    try (Connection conn =  DBSConnection.getInstance().connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(SQL))
    {

      //for each order
      while (rs.next())
      {
        int currOrderId = rs.getInt("order_id");
        ArrayList<Product> currProducts = getOrderProducts(currOrderId);

        orders.add(new Order(
            rs.getInt("order_id"),
            rs.getInt("customer_id"),
            rs.getInt("warehouse_worker_id"),
            rs.getString("status"),
            rs.getTimestamp("timestamp"),
            currProducts)
        );
      }

    } catch (SQLException ex){
      System.out.println(ex.getMessage());
    }

    return orders;
  }

  @Override
  public  ArrayList<Order> getWorkerOrdersForManager(int workerId) {
    String SQL = "select order_id,status,timestamp from eshop.orders ord join eshop.employees emp on ord.warehouse_worker_id=emp.employee_id where warehouse_worker_id=" +workerId;
    ArrayList<Order> orders = new ArrayList<>();

    try (Connection conn =  DBSConnection.getInstance().connect();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(SQL))
    {
      //for each order
      while (rs.next())
      {

        orders.add(new Order(
                rs.getInt("order_id"),
                -1,
                -1,
                rs.getString("status"),
                rs.getTimestamp("timestamp"),
                null));
      }

    } catch (SQLException ex){
      System.out.println(ex.getMessage());
    }

    return orders;
  }


  @Override public synchronized void changeOrderAssignee(Order order)
  {
    String orderSQL = "UPDATE " +SCHEMA+ "." +TABLE + " SET warehouse_worker_id = " + order.getWorkerID() + " WHERE order_id = " + order.getOrderId();
    String disableTriggerSQL = "ALTER TABLE " +SCHEMA+ "." +TABLE + " DISABLE TRIGGER ALL";
    String enableTriggerSQL = "ALTER TABLE " +SCHEMA+ "." +TABLE + " ENABLE TRIGGER ALL";

    try (Connection conn =  DBSConnection.getInstance().connect();
        Statement stmt = conn.createStatement())
    {
      if(order.getWorkerID() == -1){
        stmt.executeUpdate(disableTriggerSQL);
        int affectedRows = stmt.executeUpdate(orderSQL);
        if (affectedRows <= 0)
          throw new RuntimeException("Order status update failed");
        stmt.executeUpdate(enableTriggerSQL);
      }
      else{
        int affectedRows = stmt.executeUpdate(orderSQL);
        if (affectedRows <= 0)
          throw new RuntimeException("Order status update failed");
      }

    } catch (SQLException ex){
      System.out.println(ex.getMessage());
    }
  }

  @Override public synchronized void updateOrderState(Order order, String state)
  {
    String SQL = "UPDATE " +SCHEMA+ "." +TABLE+ " SET status = '" + order.getState() + "' WHERE order_id =" +order.getOrderId();

    try (Connection conn = DBSConnection.getInstance().connect();
        Statement stmt = conn.createStatement())
    {
      int affectedRows = stmt.executeUpdate(SQL);
      if (affectedRows <= 0)
        throw new RuntimeException("Order status update failed");

    }catch (SQLException ex) {
      System.out.println(ex.getMessage());
      throw new RuntimeException(ex.getMessage());
    }
  }

  @Override public ArrayList<Order> getOrdersForWorker(int workerID)
  {
    //fetch order's products first:

    String SQL = "SELECT * FROM " +SCHEMA+ "." +TABLE + " WHERE warehouse_worker_id=" +workerID;
    ArrayList<Order> orders = new ArrayList<>();

    try (Connection conn =  DBSConnection.getInstance().connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(SQL))
    {

      //for each order
      while (rs.next())
      {
        int currOrderId = rs.getInt("order_id");
        ArrayList<Product> currProducts = getOrderProducts(currOrderId);

        orders.add(new Order(
            rs.getInt("order_id"),
            rs.getInt("customer_id"),
            rs.getInt("warehouse_worker_id"),
            rs.getString("status"),
            rs.getTimestamp("timestamp"),
            currProducts)
        );
      }

    } catch (SQLException ex){
      System.out.println(ex.getMessage());
    }

    return orders;
  }


  private ArrayList<Product> getOrderProducts(int currOrderId){

    String productsSQL = "select * from eshop.ordered_products o_p join eshop.products prod on o_p.product_id=prod.product_id WHERE order_id=" +currOrderId;
    ArrayList<Product> currProducts = new ArrayList<>();

    try (Connection conn =  DBSConnection.getInstance().connect();
        Statement stmt = conn.createStatement();
        ResultSet productsRs = stmt.executeQuery(productsSQL))
    {
      //for each product
      while (productsRs.next())
      {
        currProducts.add(new Product(
                productsRs.getString("product_name"),
                productsRs.getString("type"),
                productsRs.getDouble("price"),
                productsRs.getString("description"),
                productsRs.getInt("quantity"),
                productsRs.getInt("product_id")
            )
        );
      }

    } catch (SQLException ex){
      System.out.println(ex.getMessage());
    }

    return currProducts;
  }


  @Override
  public void addListener(String eventName, PropertyChangeListener listener) {
    support.addPropertyChangeListener(eventName,listener);
  }

  @Override
  public void removeListener(String eventName, PropertyChangeListener listener) {
    support.removePropertyChangeListener(eventName,listener);
  }
}
