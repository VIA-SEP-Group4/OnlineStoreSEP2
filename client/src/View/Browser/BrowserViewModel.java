package View.Browser;

import Model.Product;
import Model.ProductsModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BrowserViewModel implements PropertyChangeListener
{
  private ProductsModel model;
  private StringProperty search;
  private StringProperty items;
  private StringProperty userName;

  private ObservableList<Product> browserTable;

  private BooleanProperty logOut;
  private BooleanProperty logIn;

  public BrowserViewModel(ProductsModel model)
  {
    this.model = model;
    search = new SimpleStringProperty("");
    items = new SimpleStringProperty("");
    userName = new SimpleStringProperty();

    browserTable = FXCollections.observableArrayList();

    logOut = new SimpleBooleanProperty(true);
    logIn = new SimpleBooleanProperty(false);
    model.addListener("BrowserReply",this);
  }

  public ProductsModel getModel()
  {
    return model;
  }

  public StringProperty searchProperty()
  {
    return search;
  }


  public StringProperty itemsProperty()
  {
    return items;
  }

  public StringProperty userNameProperty()
  {
    return userName;
  }


  public ObservableList<Product> getBrowserTable()
  {
    return browserTable;
  }

  public BooleanProperty logOutProperty()
  {
    return logOut;
  }

  public BooleanProperty logInProperty()
  {
    return logIn;
  }


  public void fetchProducts(){
    browserTable.addAll(model.getProducts());
  }

  public void addBasket()
  {

    items.setValue("("+model.getBasket().size()+") items");
  }

  public void reset()
  {
    browserTable.clear();
    fetchProducts();
    if(model.getId().equals(""))
    {
      logOut.setValue(true);
      logIn.setValue(false);
    }
    else {
      logOut.setValue(false);
      logIn.setValue(true);
    }
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {

  }
}
