package Model;

import Model.Models.Customer;
import Model.Models.Product;
import Networking.ProductsClient;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class ProductsModelManager implements ProductsModel, PropertyChangeListener {
    private PropertyChangeSupport support;
    private ProductsClient productsClient;
    private Customer loggedCustomer=null;
    public ProductsModelManager(ProductsClient productsClient) {
        support=new PropertyChangeSupport(this);
        this.productsClient = productsClient;
        productsClient.startClient();
        productsClient.addListener("ProductsReply",this);

    }

    @Override
    public void addProduct(Product p) {
        productsClient.addProduct(p);
    }

    @Override
    public void deleteProduct(Product p) {
        productsClient.deleteProduct(p);
    }

    @Override
    public void editProduct(Product p) {
        productsClient.editProduct(p);
    }

    @Override
    public ArrayList<Product> getAllProducts() {
        return productsClient.getAllProducts();
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

        productsClient.addToCart(p, desiredQuantity);
    }

    @Override
    public void removeFromCart(Product p, int desiredQuantity) {
        for (int i = 0; i < loggedCustomer.getCart().size(); i++)
        {
            if(loggedCustomer.getCart().get(i).getProductId() == p.getProductId())
            {
                loggedCustomer.getCart().remove(i);
            }
        }
        productsClient.addToCart(p, desiredQuantity);
    }

    @Override
    public ArrayList<Product> getCartProducts() {
        return loggedCustomer.getCart();
    }

    @Override
    public void setLoggedCustomer(Customer c) {

        loggedCustomer=c;
    }

    @Override
    public void addListener(String eventName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(eventName,listener);
    }

    @Override
    public void removeListener(String eventName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(eventName,listener);
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        support.firePropertyChange(evt);
    }
}
