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
    void addToCart(Product p, int desiredQuantity, Customer loggedCustomer);
    void addProdToStock(Product p, int prodQuantity);
    void removeFromCart(Product p, int prodQuantity, Customer loggedCustomer);
    ArrayList<Product> getCartProducts(Customer loggedCustomer);
    ArrayList<Product> getProducts(int page, int pagQuant);
    ArrayList<Product> getFilterProd(int page, int pagQuant, String type);
}
