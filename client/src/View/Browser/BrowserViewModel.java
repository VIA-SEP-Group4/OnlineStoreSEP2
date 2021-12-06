package View.Browser;

import Model.CredentialsModel;
import Model.Product;
import Model.ProductsModel;
import View.Products.TableProdViewModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class BrowserViewModel implements PropertyChangeListener
{
  private ProductsModel model;
  private CredentialsModel credsModel;
  private StringProperty search;
  private StringProperty items;
  private StringProperty userName;

  private ObservableList<Product> browserTable;
  private ObjectProperty<Product> selectedProd;

  private BooleanProperty logOut;
  private BooleanProperty logIn;

  public BrowserViewModel(ProductsModel model,CredentialsModel credsModel)
  {
    this.model = model;
    search = new SimpleStringProperty("");
    items = new SimpleStringProperty("");
    userName = new SimpleStringProperty();
    this.credsModel=credsModel;
    browserTable = FXCollections.observableArrayList();
    selectedProd = new SimpleObjectProperty<>();

    logOut = new SimpleBooleanProperty(true);
    logIn = new SimpleBooleanProperty(false);

    model.addListener("ProductsReply",this);
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

  public void setSelectedProd(Product selectedProd)
  {
    this.selectedProd.set(selectedProd);
  }

  public Product getSelectedProd()
  {
    return selectedProd.get();
  }

  public void fetchProducts(){
    browserTable.addAll(model.getProducts());
  }

  public void reset()
  {
    browserTable.clear();
    fetchProducts();
    if(credsModel.getLoggedCustomer() == null)
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
    ArrayList<Product> products = (ArrayList<Product>) evt.getNewValue();
    browserTable.addAll(products);
  }

  public void addToCart(Product p, int desiredQuantity)
  {
    if (desiredQuantity>0 && desiredQuantity<=p.getQuantity())
    {
      model.addToCart(p, desiredQuantity);
      reset();
    }
    else
    {
      //TODO .. put it in some label so customer can see what's going on
      System.out.println("error label ->wrong quantity ...");
    }
  }
}
