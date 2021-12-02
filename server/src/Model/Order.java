package Model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Order implements Serializable
{
  private int orderId;
  private ArrayList<Product> products;
  private String state;
  private Timestamp timestamp;
  private int customerId;

  public Order(int orderId, int customerId)
  {
    this.orderId = orderId;
    this.customerId = customerId;
    products = new ArrayList<>();
    state = "waiting";
    timestamp = new Timestamp(System.currentTimeMillis());
  }

  public Order(int orderId, int customerId, ArrayList<Product> products)
  {
    this.orderId = orderId;
    this.customerId = customerId;
    this.products = products;
    state = "waiting";
    timestamp = new Timestamp(System.currentTimeMillis());
  }

  public int getOrderId()
  {
    return orderId;
  }

  public int getCustomerId()
  {
    return customerId;
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
