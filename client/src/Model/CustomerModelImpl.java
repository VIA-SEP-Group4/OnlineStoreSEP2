package Model;

import Model.Models.Customer;
import Model.Models.Order;
import Model.Models.Product;
import Networking.CustomerClient;
import Networking.LoginClient;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class CustomerModelImpl implements CustomerModel, PropertyChangeListener {
    private PropertyChangeSupport support;
    private CustomerClient customerClient;
    private LoginClient loginClient;
    private Customer loggedCustomer = null;

    public CustomerModelImpl(CustomerClient customerClient, LoginClient loginClient) {
        this.customerClient = customerClient;
        this.loginClient = loginClient;
        customerClient.startClient();
        if(!loginClient.isStarted()) loginClient.startClient();
        support=new PropertyChangeSupport(this);

        this.loginClient.addListener("LoggedCustomerObj", this::setLoggedCustomer);
        this.customerClient.addListener("ProductsReply",this);
        this.customerClient.addListener("newOrder", this);
    }


    @Override
    public void processOrder(Order o) {
        loggedCustomer.getCart().clear();
        customerClient.processOrder(o);
    }

    @Override
    public void addToCart(Product p, int desiredQuantity) {
        boolean contains = false;
        for (Product currP : loggedCustomer.getCart()){
            if (currP.getProductId()==p.getProductId()){
                currP.setQuantityP(currP.getQuantity()+desiredQuantity);
                contains = true;
            }
        }

        if (!contains){
            Product orderedProduct = p.copy();
            orderedProduct.setQuantityP(desiredQuantity);
            loggedCustomer.getCart().add(orderedProduct);
        }

        customerClient.addToCart(p, desiredQuantity);
    }

    @Override public void removeFromCart(Product p, int desiredQuantity)
    {
        for (int i = 0; i < loggedCustomer.getCart().size(); i++)
        {
            if(loggedCustomer.getCart().get(i).getProductId() == p.getProductId())
            {
                loggedCustomer.getCart().remove(i);
            }
        }
        customerClient.addToCart(p, desiredQuantity);
    }


    @Override public ArrayList<Order> fetchCustomerOrders() {
        return customerClient.getOrders(loggedCustomer.getCustomerId());
    }

    @Override
    public ArrayList<Product> getCartProducts() {
        return loggedCustomer.getCart();
    }

    @Override
    public ArrayList<Product> getProducts() {
        return customerClient.getProducts();
    }

    @Override
    public Customer getLoggedCustomer() {
        loggedCustomer= loginClient.getLoggedCustomer();
        return loggedCustomer;
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
    public void propertyChange(PropertyChangeEvent evt) {
        support.firePropertyChange(evt);
    }

    private void setLoggedCustomer(PropertyChangeEvent evt)
    {
        loggedCustomer = (Customer) evt.getNewValue();
    }
}
