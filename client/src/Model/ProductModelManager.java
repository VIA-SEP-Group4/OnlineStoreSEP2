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
    private ArrayList<Product> cart;
    private PropertyChangeSupport support;

    public ProductModelManager(CustomerClient customerClient, ManagerClient managerClient) {
        this.customer = customerClient;
        this.manager=managerClient;
        manager.startClient();
        customer.startClient();
        cart=new ArrayList<>();
        support=new PropertyChangeSupport(this);
        customerClient.addListener("ProductsReply",this);
    }

    @Override public ArrayList<Product> getCartProducts()
    {
        return cart;
    }

    @Override public void processOrder(Order newOrder)
    {
        cart.clear();
        customer.processOrder(newOrder);
    }

    @Override public ArrayList<Order> fetchOrders()
    {
        return customer.getOrders();
    }

    @Override public void addToCart(Product p, int desiredQuantity)
    {
        boolean contains = false;
        for (Product currP : cart){
            if (currP.getProductId()==p.getProductId()){
                currP.setQuantityP(currP.getQuantity()+desiredQuantity);
                contains = true;
            }
        }

        if (!contains){
            Product orderedProduct = p.copy();
            orderedProduct.setQuantityP(desiredQuantity);
            cart.add(orderedProduct);
        }

        customer.addToCart(p, desiredQuantity);
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
        cart.add(product);
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
