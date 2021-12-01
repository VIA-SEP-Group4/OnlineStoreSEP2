package Model;

import Networking.Client;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ProductModelManager implements ProductsModel, PropertyChangeListener {
    private Client client;
    private ArrayList<Product> basket;
    private PropertyChangeSupport support;

    public ProductModelManager(Client client) {
        this.client = client;
        basket=new ArrayList<>();
        support=new PropertyChangeSupport(this);
        client.addListener("ProductsReply",this);
    }

    @Override public ArrayList<Product> getBasket()
    {
        return basket;
    }

    @Override public String getId()
    {
        return client.getID();
    }

    @Override public User getLoggedUser()
    {
        return client.getLoggedUser();
    }

    @Override public void processOrder(Order newOrder)
    {
        client.processOrder(newOrder);
    }

    @Override
    public void addProduct(Product p) {
        client.addProduct(p);
    }

    @Override public void deleteProduct(Product p)
    {
        client.deleteProduct(p);
    }

    @Override public ArrayList<Product> getProducts()
    {
        return client.getProducts();
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
