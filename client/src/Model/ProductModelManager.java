package Model;

import Networking.CustomerClient;
import Networking.ManagerClient;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ProductModelManager implements ProductsModel, PropertyChangeListener {
    private CustomerClient customer;
    private ManagerClient manager;
    private ArrayList<Product> basket;
    private PropertyChangeSupport support;

    public ProductModelManager(CustomerClient customerClient, ManagerClient managerClient) {
        this.customer = customerClient;
        this.manager=managerClient;
        manager.startClient();
        customer.startClient();
        basket=new ArrayList<>();
        support=new PropertyChangeSupport(this);
        customerClient.addListener("ProductsReply",this);
    }

    @Override public ArrayList<Product> getBasket()
    {
        return basket;
    }

    @Override public void processOrder(Order newOrder)
    {
        customer.processOrder(newOrder);
    }

    @Override public ArrayList<Order> fetchOrders()
    {
        return customer.getOrders();
    }

    @Override
    public void addProduct(Product p) {
        manager.addProduct(p);
    }

    @Override public void deleteProduct(Product p)
    {
        manager.deleteProduct(p);
    }

    @Override public ArrayList<Product> getProducts()
    {
        return customer.getProducts();
    }

    @Override public void addBasket(Product product)
    {
        basket.add(product);

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        support.firePropertyChange(evt);
    }

    @Override
    public void addListener(String eventName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(eventName,listener);
    }

    @Override
    public void removeListener(String eventName, PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
