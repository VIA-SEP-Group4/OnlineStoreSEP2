package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Customer implements Serializable
{

  private String username;
  private String password;
  private String email;
  private String firstName;
  private String lastName;
  private ArrayList<Order> orders;
  private int userId=-1;

  public Customer(String username, String password, String email, String firstName, String lastName) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;

    orders = new ArrayList<>();
  }



  public String getEmail() {
    return email;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public int getUserId()
  {
    return userId;
  }

  public ArrayList<Order> getOrders()
  {
    return orders;
  }

  public void setOrders(ArrayList<Order> orders)
  {
    this.orders = orders;
  }
}
