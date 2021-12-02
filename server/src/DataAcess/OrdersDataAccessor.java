package DataAcess;

import Model.Order;

import java.util.ArrayList;

public interface OrdersDataAccessor
{
  void addNewOrder(Order newOrder);

  //get all orders
  ArrayList<Order> getOrders();
  //get orders by customer
  ArrayList<Order> getOrders(int customerId);
  //get orders by status
  ArrayList<Order> getOrders(String orderStatus);
}
