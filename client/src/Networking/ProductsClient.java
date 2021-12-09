package Networking;

import Model.Models.Product;
import Utils.Subject;

import java.util.ArrayList;

public interface ProductsClient extends Subject {
    void startClient();
    void addProduct(Product p);
    void deleteProduct(Product p);
    void editProduct(Product p);
    ArrayList<Product> getAllProducts();
    void addToCart(Product p, int desiredQuantity);
}
