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
    void updateStock(Product p, int prodQuantity);
    ArrayList<Product> getProducts(int page, int pagQuant);
    ArrayList<Product> getFilterProd(int page, int pagQuant, String type);
}
