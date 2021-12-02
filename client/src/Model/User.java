package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable
{

  public enum UserType
  {
    CUSTOMER,
    MANAGER,
    WORKER,
    ADMIN
  }

  private String username;
  private String password;
  private String email;
  private String firstName;
  private String lastName;
  private ArrayList<Order> orders;
  private UserType userType;
  private int userId;

  public User(String username, String password, String email, String firstName, String lastName, UserType userType) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;

    orders = new ArrayList<>();
    this.userType = userType;
  }
  //for customers
  public User(String username, String password, String email, String firstName, String lastName, int userId) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.userId = userId;

    orders = new ArrayList<>();
    userType = UserType.CUSTOMER;
  }
  public User(String username, String password, String email, String firstName, String lastName) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.userId = -1;

    orders = new ArrayList<>();
    userType = UserType.CUSTOMER;
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

  public UserType getUserType()
  {
    return userType;
  }
}
