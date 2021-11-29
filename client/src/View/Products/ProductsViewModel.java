package View.Products;

import Model.Product;
import Model.ProductsModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class ProductsViewModel implements PropertyChangeListener
{
  private ObservableList<TableProdViewModel> products;
 
  private ProductsModel model;

  public ProductsViewModel(ProductsModel model)
  {
    this.model = model;
    this.products = FXCollections.observableArrayList();
    
    model.addListener("AddProductReply",this);
  }

  public ObservableList<TableProdViewModel> getProducts()
  {
    return products;
  }

  public void removeProduct(Product p){
    model.deleteProduct(p);
  }

  public void getProd(){
    ArrayList<Product> prod = model.getProducts();
    for (Product product:prod)
    {
      products.add(new TableProdViewModel(product));
    }
  }
  @Override public void propertyChange(PropertyChangeEvent evt)
  {

  }

  public void reset()
  {
    products.clear();
    getProd();
  }
}
