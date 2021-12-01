package DataAcess;

import Model.Order;
import Model.Product;

import java.beans.PropertyChangeSupport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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
}
