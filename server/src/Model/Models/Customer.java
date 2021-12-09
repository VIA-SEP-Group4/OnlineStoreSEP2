package Model.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Customer implements Serializable
{

  private int customerId = -1;
  private String username;
  private String password;
  private String email;
  private String firstName;
  private String lastName;

  private ArrayList<Order> orders;
  private ArrayList<Product> cart;

  //when registered ->created without ID(DB takes care of it)
  public Customer(String username, String password, String email, String firstName, String lastName) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;

    cart = new ArrayList<>();
    orders = new ArrayList<>();
  }

  //when fetched from DB ->ID must be fetched and assigned to the object!!!
  public Customer(String username, String password, String email, String firstName, String lastName, int customerId) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.customerId = customerId;

    orders = new ArrayList<>();
    cart = new ArrayList<>();
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

  public int getCustomerId()
  {
    return customerId;
  }

  public ArrayList<Order> getOrders()
  {
    return orders;
  }

  public ArrayList<Product> getCart() {
    return cart;
  }

  public void setOrders(ArrayList<Order> orders)
  {
    this.orders = orders;
  }

  @Override
  public String toString() {
    return "Customer{" +
        "username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", email='" + email + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", orders=" + orders +
        ", customerId=" + customerId +
        '}';
  }
}
