package Networking;

import Model.Models.Order;

import java.beans.PropertyChangeEvent;
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
    private OrdersServerRemote ordersServer;

    public OrdersClientImpl() {
        support=new PropertyChangeSupport(this);
    }

    @Override
    public void startClient() {
        try {
            //export client
            UnicastRemoteObject.exportObject(this, 0);

            //lookup server stub
            ordersServer = (OrdersServerRemote) Naming.lookup("rmi://localhost:1099/ordersServer");
//            ordersServer.registerClient(this);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.err.println("failed to initialize client-object ...[OrdersClientImpl.startClient()]");
        }
    }

    @Override
    public ArrayList<Order> getAllOrders() {
        try {
            return ordersServer.getAllOrders();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override public ArrayList<Order> getOrders(String status)
    {
        ArrayList<Order> orders = null;
        try
        {
            orders = ordersServer.getOrders(status);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public void changeOrderAssignee(Order order) {
        try {
            ordersServer.changeOrderAssignee(order);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override public void updateOrderState(Order order, String state)
    {
        try
        {
            ordersServer.updateOrderState(order, state);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Order> getWorkerOrdersForManager(int workerID) {
        try {
            return ordersServer.getWorkerOrdersForManager(workerID);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void processOrder(Order newOrder) {
        try {
            ordersServer.addNewOrder(newOrder);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Order> getCustomerOrders(int customerId) {
        try {
            return ordersServer.getCustomerOrders(customerId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override public void unregisterClient()
    {
        try
        {
            ordersServer.removeClient(this);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    @Override public void registerClient()
    {
        try
        {
            ordersServer.registerClient(this);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
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
    public void receiveUpdatedOrder(Object updatedOrder) throws RemoteException {
        PropertyChangeEvent event=(PropertyChangeEvent) updatedOrder;
        if(event.getPropertyName().equals("newOrder")){
        support.firePropertyChange("newOrder",null,event.getNewValue());}
        else{
            support.firePropertyChange("updatedOrderStatus",null,event.getNewValue());
        }
    }
}
