package DataAcess;

import Model.Order;
import Model.Product;

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

  @Override public void addNewOrder(Order newOrder)
  {
    String SQL = "INSERT INTO " +SCHEMA+ "." +TABLE+ "(order_id, customer_id, timestamp) " + "VALUES(?,?,?)";

    try (Connection conn = DBSConnection.getInstance().connect();
        PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS))
    {
      System.out.println(newOrder);
      pstmt.setInt(1, newOrder.getOrderId());
      pstmt.setInt(2, newOrder.getCustomerId());
      pstmt.setTimestamp(3, newOrder.getTimestamp());


      int affectedRows = pstmt.executeUpdate();
      if (affectedRows <= 0)
        throw new RuntimeException("Product insertion failed");
    }
    catch (SQLException ex) {
      System.out.println(ex.getMessage());
      throw new RuntimeException(ex.getMessage());
    }


    SQL = "INSERT INTO " +SCHEMA+ "." +TABLE2+ "(product_id, order_id, quantity) " + "VALUES(?,?,?)";

    try (Connection conn = DBSConnection.getInstance().connect();
        PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS))
    {
      for (Product p : newOrder.getProducts())
      {
        System.out.println(p);
        pstmt.setInt(1, p.getProductId());
        pstmt.setInt(2, newOrder.getOrderId());
        pstmt.setInt(3, p.getQuantity());

        int affectedRows = pstmt.executeUpdate();
        if (affectedRows <= 0)
          throw new RuntimeException("Product insertion failed");
      }
    }
    catch (SQLException ex) {
      System.out.println(ex.getMessage());
      throw new RuntimeException(ex.getMessage());
    }
  }

  @Override public ArrayList<Order> getOrders()
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

  @Override public ArrayList<Order> getOrders(int customerId)
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

  @Override public ArrayList<Order> getOrders(String orderStatus)
  {
    return null;
  }

}
