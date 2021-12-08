package View.Manager;

import Model.ManagerModel;
import Model.Models.Product;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class ProductsViewModel implements PropertyChangeListener
{
  private ObservableList<Product> products;
 
  private ManagerModel managerModel;

  public ProductsViewModel(ManagerModel managerModel)
  {
    this.managerModel = managerModel;
    this.products = FXCollections.observableArrayList();
    products.setAll(managerModel.getProducts());
    managerModel.addListener("ProductsReply",this);
  }

  public ObservableList<Product> getProducts()
  {
    return products;
  }

  public void removeProduct(Product p){
    managerModel.deleteProduct(p);
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
