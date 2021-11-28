package Model;

import Networking.Client;
import Utils.Subject;

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
    }

    @Override public ArrayList<Product> getBasket()
    {
        return basket;
    }
    @Override public String getId()
    {
        try
        {
            return client.getID();
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        } return "";
    }

    @Override
    public void addProduct(Product p) {
        try {
            client.addProduct(p);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
