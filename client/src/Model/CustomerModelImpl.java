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
    private ArrayList<Product> cart;
    private PropertyChangeSupport support;
    private CustomerClient customer;
    private LoginClient login;
    private Customer loggedCustomer = null;

    public CustomerModelImpl(CustomerClient customer, LoginClient login) {
        this.customer = customer;
        this.login = login;
        customer.startClient();
        if(!login.isStarted()) login.startClient();
        support=new PropertyChangeSupport(this);
        cart=new ArrayList<>();

        login.addListener("LoggedCustomerObj", this::setLoggedCustomer);
        customer.addListener("ProductsReply",this);
    }


    @Override
    public void processOrder(Order o) {
        cart.clear();
        customer.processOrder(o);
    }

    @Override
    public void addToCart(Product p,int desiredQuantity) {
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

    @Override public void removeFromCart(Product p, int desiredQuantity)
    {
        for (int i = 0; i < cart.size(); i++)
        {
            if(cart.get(i).getProductId()==p.getProductId())
            {
                cart.remove(i);
            }
        }
        customer.addToCart(p, desiredQuantity);
    }

    public void addToCart(Product p){
        cart.add(p);
    }

    @Override public ArrayList<Order> fetchCustomerOrders() {
        return customer.getOrders(loggedCustomer.getCustomerId());
    }

    @Override
    public ArrayList<Product> getCartProducts() {
        return cart;
    }

    @Override
    public ArrayList<Product> getProducts() {
        return customer.getProducts();
    }

    @Override
    public Customer getLoggedCustomer() {
        loggedCustomer=login.getLoggedCustomer();
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
