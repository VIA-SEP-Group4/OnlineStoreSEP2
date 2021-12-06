package DataAcess;

import Model.Models.Order;

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
  //get orders by workers
  ArrayList<Order> getWorkerOrders(int workerId);

}
