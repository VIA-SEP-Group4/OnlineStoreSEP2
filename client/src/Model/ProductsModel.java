package Model;

import Model.Models.Customer;
import Model.Models.Product;
import Utils.Subject;

import java.util.ArrayList;

public interface ProductsModel extends Subject {
    void addProduct(Product p);
    void deleteProduct(Product p);
    void editProduct(Product p);
    ArrayList<Product> getAllProducts();
    void addToCart(Product p, int desiredQuantity);
    void removeFromCart(Product p, int desiredQuantity);
    ArrayList<Product> getCartProducts();
    void setLoggedCustomer(Customer c);
}
