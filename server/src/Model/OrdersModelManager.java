package Model;

import DataAcess.OrdersDataAccessor;
import DataAcess.OrdersDataManager;
import Model.Models.Order;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class OrdersModelManager implements OrdersModel, PropertyChangeListener
{

  private OrdersDataAccessor ordersDataAccessor;
  private PropertyChangeSupport support;

  public OrdersModelManager()
  {
    ordersDataAccessor = new OrdersDataManager();
    support=new PropertyChangeSupport(this);

    ordersDataAccessor.addListener("newOrder",this);
    ordersDataAccessor.addListener("updatedOrderStatus",this);
  }


  @Override public ArrayList<Order> getAllOrders()
  {
    return ordersDataAccessor.getAllOrders();
  }
  @Override public ArrayList<Order> getOrders(int customerId)
  {
    return ordersDataAccessor.getOrders(customerId);
  }

  @Override public ArrayList<Order> getOrders(String status)
  {
    return ordersDataAccessor.getOrders(status);
  }

  @Override
  public ArrayList<Order> getWorkerOrdersForManager(int workerID) {
    return ordersDataAccessor.getWorkerOrdersForManager(workerID);
  }

  @Override
  public ArrayList<Order> getOrdersForWorker(int workerID) {
    return null;
  }


  @Override public void addNewOrder(Order newOrder)
  {
    ordersDataAccessor.addNewOrder(newOrder);
  }


  @Override public void changeOrderAssignee(Order order)
  {
    ordersDataAccessor.changeOrderAssignee(order);
  }

  @Override public void updateOrderState(Order order, String state)
  {
    ordersDataAccessor.updateOrderState(order,state);
  }

  @Override public void addListener(String eventName, PropertyChangeListener listener) {
    support.addPropertyChangeListener(eventName,listener);
  }

  @Override public void removeListener(String eventName, PropertyChangeListener listener) {
    support.removePropertyChangeListener(eventName,listener);
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    support.firePropertyChange(evt.getPropertyName(),evt.getOldValue(),evt.getNewValue());
  }
}
