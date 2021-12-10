package Networking;

import Model.Models.Order;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class OrdersClientImpl implements OrdersClient, OrdersClientRemote {
    private PropertyChangeSupport support;
    private OrdersServerRemote serverStub;

    public OrdersClientImpl() {
        support=new PropertyChangeSupport(this);
    }

    @Override
    public void startClient() {
        try {
            //export client
            UnicastRemoteObject.exportObject(this, 0);

            //lookup server stub
            serverStub = (OrdersServerRemote) Naming.lookup("rmi://localhost:1099/ordersServer");
            serverStub.registerClient(this);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.err.println("failed to initialize client-object ...[OrdersClientImpl.startClient()]");
        }
    }

    @Override
    public ArrayList<Order> getAllOrders() {
        try {
            return serverStub.getAllOrders();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override public ArrayList<Order> getOrders(String status)
    {
        return serverStub.getOrders(status);
    }

    @Override
    public void changeOrderAssignee(Order order) {
        try {
            serverStub.changeOrderAssignee(order);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override public void updateOrderState(Order order, String state)
    {
        try
        {
            serverStub.updateOrderState(order, state);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Order> getWorkerOrdersForManager(int workerID) {
        try {
            return serverStub.getWorkerOrdersForManager(workerID);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void processOrder(Order newOrder) {
        try {
            serverStub.addNewOrder(newOrder);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Order> getCustomerOrders(int customerId) {
        try {
            return serverStub.getCustomerOrders(customerId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addListener(String eventName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(eventName,listener);

    }

    @Override
    public void removeListener(String eventName, PropertyChangeListener listener) {
        support.removePropertyChangeListener(eventName,listener);
    }

    @Override
    public void receiveUpdatedOrders(Object orders) throws RemoteException {
        support.firePropertyChange("newOrder",null,orders);
    }
}
