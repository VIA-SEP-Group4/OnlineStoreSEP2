package Networking;

import Model.Models.Order;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface OrdersServerRemote extends Remote {
    void addNewOrder(Order newOrder) throws RemoteException;
    ArrayList<Order> getCustomerOrders(int customerId) throws RemoteException;
    ArrayList<Order> getAllOrders() throws RemoteException;
    ArrayList<Order> getOrders(String status) throws RemoteException;
    ArrayList<Order> getWorkerOrdersForManager(int workerID) throws RemoteException;
    ArrayList<Order> getOrdersForWorker(int workerID) throws RemoteException;
    void updateOrderState(Order order, String state) throws RemoteException;
    void registerClient(OrdersClientRemote client) throws RemoteException;
    void removeClient(OrdersClientRemote client) throws RemoteException;
}
