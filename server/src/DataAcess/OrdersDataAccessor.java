package DataAcess;

import Model.Models.Order;
import Utils.Subject;

import java.util.ArrayList;

public interface OrdersDataAccessor extends Subject
{
  void addNewOrder(Order newOrder);

  //get all orders
  ArrayList<Order> getAllOrders();
  //get orders by customer
  ArrayList<Order> getOrders(int customerId);
  //get orders by status
  ArrayList<Order> getOrders(String orderStatus);
  //get orders by workers
  ArrayList<Order> getWorkerOrdersForManager(int workerId);

}
