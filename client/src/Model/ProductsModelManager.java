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

    public ProductsModelManager(ProductsClient productsClient) {
        support=new PropertyChangeSupport(this);
        this.productsClient = productsClient;
        productsClient.startClient();
        productsClient.addListener("ProductsReply",this);
        productsClient.addListener("productDeleted",this);

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
    public void addToCart(Product p, int desiredQuantity, Customer loggedCustomer) {
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

        productsClient.updateStock(p, -desiredQuantity);
    }

    @Override
    public void addProdToStock(Product p, int prodQuantity){
        productsClient.updateStock(p, prodQuantity);
    }
    @Override
    public void removeFromCart(Product p, int prodQuantity, Customer loggedCustomer) {
        for (int i = 0; i < loggedCustomer.getCart().size(); i++)
        {
            if(loggedCustomer.getCart().get(i).getProductId() == p.getProductId())
            {
                loggedCustomer.getCart().remove(i);
            }
        }
        productsClient.updateStock(p, prodQuantity);
    }

    @Override public ArrayList<Product> getProducts(int page, int pagQuant)
    {
        return productsClient.getProducts(page,pagQuant);
    }

    @Override public ArrayList<Product> getFilterProd(int page, int pagQuant, String type)
    {
        return productsClient.getFilterProd(page,pagQuant,type);
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
