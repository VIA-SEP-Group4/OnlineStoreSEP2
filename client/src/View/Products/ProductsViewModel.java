package View.Products;

import Model.ManagerModel;
import Model.Models.Product;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class ProductsViewModel implements PropertyChangeListener
{
  private ObservableList<TableProdViewModel> products;
 
  private ManagerModel managerModel;

  public ProductsViewModel(ManagerModel managerModel)
  {
    this.managerModel = managerModel;
    this.products = FXCollections.observableArrayList();
    
    managerModel.addListener("ProductsReply",this);
  }

  public ObservableList<TableProdViewModel> getProducts()
  {
    return products;
  }

  public void removeProduct(Product p){
    managerModel.deleteProduct(p);
  }

  public void getProd(){
    ArrayList<Product> prod = managerModel.getProducts();
    for (Product product:prod)
    {
      products.add(new TableProdViewModel(product));
    }
  }
  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    ArrayList<Product> product= (ArrayList<Product>) evt.getNewValue();
    for(Product p : product){
      products.add(new TableProdViewModel(p));
    }
  }

  public void reset()
  {
    products.clear();
    getProd();
  }
}
