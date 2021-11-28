package DataAcess;

import Model.Product;

import java.util.ArrayList;

public interface ProductsDataAcessor {
    ArrayList<Product> getProducts();

    void addProduct(Product p);
}
