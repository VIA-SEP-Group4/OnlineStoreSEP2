package DataAcess;

import Model.Product;

import java.util.ArrayList;

public interface ProductsDataAcessor {
    ArrayList<Product> getProducts(int amount);

    void addProduct(Product p);
}
