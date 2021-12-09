package View.Manager;

import Model.Models.Product;

import Model.ProductsModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class ProductsViewModel implements PropertyChangeListener
{
  private ObservableList<Product> products;
 
  private ProductsModel productsModel;

  public ProductsViewModel(ProductsModel productsModel)
  {
    this.productsModel = productsModel;
    this.products = FXCollections.observableArrayList();
    products.setAll(productsModel.getAllProducts());
    productsModel.addListener("ProductsReply",this);
  }

  public ObservableList<Product> getProducts()
  {
    return products;
  }

  public void removeProduct(Product p){
    productsModel.deleteProduct(p);
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    ArrayList<Product> product= (ArrayList<Product>) evt.getNewValue();
    products.setAll(product);
  }

  public void reset()
  {
    products.clear();
  }
}
