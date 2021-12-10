package Model;

import Model.Models.Order;
import Model.Models.Product;
import Utils.Subject;

import java.util.ArrayList;

public interface OrdersModel extends Subject
{
  void addNewOrder(Order newOrder);
  ArrayList<Order> getOrders(int customerId);
  ArrayList<Order> getOrders(String status);
  ArrayList<Order> getAllOrders();
  ArrayList<Order> getWorkerOrdersForManager(int workerID);
  ArrayList<Order> getOrdersForWorker(int workerID);
  void changeOrderAssignee(Order order);
  void updateOrderState(Order order, String state);
}
