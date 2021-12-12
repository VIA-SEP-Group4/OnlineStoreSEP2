package Model.Models;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Order implements Serializable
{
  private int orderId = -1;
  private ArrayList<Product> products;
  private String state = "waiting";
  private Timestamp timestamp;
  private int customerId;
  private int wwId = -1;
  @Serial
  private static final long serialVersionUID = 2L;
  //instantiation when created in GUI ->to be uploaded
  public Order(int customerId, ArrayList<Product> products)
  {
    this.customerId = customerId;
    this.products = products;

    timestamp = new Timestamp(System.currentTimeMillis());
  }
  //instantiation when fetching from DB
  public Order(int orderId, int customerId, int wwId, String status, Timestamp timestamp, ArrayList<Product> products)
  {
    this.orderId = orderId;
    this.customerId = customerId;
    this.products = products;
    this.wwId = wwId;
    this.state = status;
    this.timestamp = timestamp;
  }

  public int getOrderId()
  {
    return orderId;
  }
  public void setOrderId(int orderId)
  {
    this.orderId = orderId;
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

  public void setStatus(String status){
    state = status;
  }

  public void setWorkerID(int wwId)
  {
    this.wwId = wwId;
  }

  public int getWorkerID()
  {
    return wwId;
  }

  public String getTimestamp()
  {
    String t = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(timestamp);
    return t;
  }

  public int getWwId() {
    return wwId;
  }

  public Timestamp getTimestampSQL(){
    return timestamp;
  }

  public ArrayList<Product> getProducts()
  {
    return products;
  }

  @Override public String toString()
  {
    return "Order{" + "orderId=" + orderId + ", products=" + products
        + ", state='" + state + '\'' + ", timestamp=" + timestamp
        + ", customerId=" + customerId + ", wwId=" + wwId + '}';
  }
}
