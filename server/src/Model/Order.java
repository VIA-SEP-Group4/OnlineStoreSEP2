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
  private int wwId;

//  public Order(int orderId, int customerId)
//  {
//    this.orderId = orderId;
//    this.customerId = customerId;
//    products = new ArrayList<>();
//    state = "waiting";
//    timestamp = new Timestamp(System.currentTimeMillis());
//  }

  //instantiation when creating from GUI ->to be uploaded
  public Order(int orderId, int customerId, ArrayList<Product> products)
  {
    this.orderId = orderId;
    this.customerId = customerId;
    this.products = products;

    state = "waiting";
    timestamp = new Timestamp(System.currentTimeMillis());
    wwId = -1;
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