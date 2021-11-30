package Model;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Order
{
  private int orderId;
  private ArrayList<Product> products;
  private String state;
  private Timestamp timestamp;

  public Order(int order_id)
  {
    this.orderId = order_id;
    products = new ArrayList<>();
    state = "waiting";
    timestamp = new Timestamp(System.currentTimeMillis());
  }

  public Order(int order_id, ArrayList<Product> products)
  {
    this.orderId = order_id;
    this.products = products;
    state = "waiting";
    timestamp = new Timestamp(System.currentTimeMillis());
  }

  public int getOrderId()
  {
    return orderId;
  }

  public double getTotalPrice(){
    double price = 0;
    for (Product p : products)
      price += p.getPrice() * p.getQuantity();
    return price;
  }

  public String getState()
  {
    return state;
  }

  public Timestamp getTimestamp()
  {
    return timestamp;
  }

  public ArrayList<Product> getProducts()
  {
    return products;
  }
}
