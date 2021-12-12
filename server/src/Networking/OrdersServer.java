package Networking;

import Model.Models.Order;
import Model.OrdersModel;

import java.beans.PropertyChangeEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class OrdersServer implements OrdersServerRemote {
    private OrdersModel modelManager;
    private ArrayList<OrdersClientRemote> clients;

    public OrdersServer(OrdersModel modelManager) {
        this.modelManager = modelManager;
        clients=new ArrayList<>();
        modelManager.addListener("newOrder",this::ordersUpdate);
        modelManager.addListener("updatedOrderStatus",this::ordersUpdate);
    }


    public void start() throws RemoteException, MalformedURLException {
        UnicastRemoteObject.exportObject(this, 1099);
        Naming.rebind("ordersServer", this);
        System.out.println("Orders server started");
    }

    private void ordersUpdate(PropertyChangeEvent event) {
        for(OrdersClientRemote client: clients){
            try {
                client.receiveUpdatedOrder(event);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addNewOrder(Order newOrder) throws RemoteException {
        modelManager.addNewOrder(newOrder);
    }

    @Override
    public ArrayList<Order> getCustomerOrders(int customerId) throws RemoteException {
        return modelManager.getOrders(customerId);
    }

    @Override
    public ArrayList<Order> getAllOrders() throws RemoteException {
        return modelManager.getAllOrders();
    }

    @Override public ArrayList<Order> getOrders(String status)
    {
        return modelManager.getOrders(status);
    }

    @Override
    public ArrayList<Order> getWorkerOrdersForManager(int workerID) throws RemoteException {
        return modelManager.getWorkerOrdersForManager(workerID);
    }

    @Override
    public ArrayList<Order> getOrdersForWorker(int workerID) throws RemoteException {
        return modelManager.getOrdersForWorker(workerID);
    }

    @Override
    public void changeOrderAssignee(Order order) throws RemoteException {
        modelManager.changeOrderAssignee(order);
    }

    @Override
    public void updateOrderState(Order order, String state) throws RemoteException {
        modelManager.updateOrderState(order,state);
    }

    @Override
    public void registerClient(OrdersClientRemote client) throws RemoteException {
        clients.add(client);
    }

    @Override
    public void removeClient(OrdersClientRemote client) throws RemoteException {
        clients.remove(client);
    }
}
