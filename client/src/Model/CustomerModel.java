package Model;

import Model.Models.Customer;
import Model.Models.Order;
import Model.Models.Product;
import Utils.Subject;

import java.util.ArrayList;

public interface CustomerModel  extends Subject {
    void processOrder(Order o);
    void addToCart(Product p, int desiredQuantity);
    void removeFromCart(Product p, int desiredQuantity);
    void addToCart(Product p);

    ArrayList<Order> fetchCustomerOrders();
    ArrayList<Product> getCartProducts();
    ArrayList<Product> getProducts();

    Customer getLoggedCustomer();
}
