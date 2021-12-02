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
  private ObjectProperty<TableProdViewModel> selectedProd;

  private BooleanProperty logOut;
  private BooleanProperty logIn;

  private ArrayList<Product> basket;

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

    basket = new ArrayList<>();
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

  public void setSelectedProd(TableProdViewModel selectedProd)
  {
    this.selectedProd.set(selectedProd);
  }

  public TableProdViewModel getSelectedProd()
  {
    return selectedProd.get();
  }

  public void fetchProducts(){
    ArrayList<Product> products = model.getProducts();
    for (Product p : products)
    {
      browserTable.add(p);
    }
  }

  public void addBasket()
  {
     ArrayList<Product> basket = model.getBasket();

      boolean add = true;
      if (selectedProd.get() != null && selectedProd.get().quantityPropertyProperty().get() != 0 && hasProducts()) {
        Product prod = new Product(selectedProd.get().namePropertyProperty().get(),selectedProd.get().typePropertyProperty().get(),
            selectedProd.get().pricePropertyProperty().get(),selectedProd.get().descriptionProperty().get(),1);

        for (int i = 0; i < basket.size(); i++)
        {
          if (basket.get(i).getName().equals(prod.getName()))
          {
            basket.get(i).setQuantityP(basket.get(i).getQuantity() + 1);
            add = false;
            break;
          }
        }
        if (add)
          model.addBasket(prod);
      }
      items.setValue("("+itemQuantity()+") items");
  }

    public boolean hasProducts()
    {
      ArrayList<Product> basket = model.getBasket();
      Product prod = new Product(selectedProd.get().namePropertyProperty().get(),selectedProd.get().typePropertyProperty().get(),
          selectedProd.get().pricePropertyProperty().get(),selectedProd.get().descriptionProperty().get(),selectedProd.get().quantityPropertyProperty().get());
      if (basket.size() != 0)
      {
        for (int i = 0; i < basket.size(); i++)
        {
          if (basket.get(i).getName().equals(prod.getName())
              && basket.get(i).getQuantity() >= prod.getQuantity())
            return false;
        }
      } return true;
    }

    public int itemQuantity(){
    int iQ = 0;
    for (int i = 0; i < model.getBasket().size(); i++)
    {
      iQ += model.getBasket().get(i).getQuantity();
    }
    return iQ;
  }

  public void reset()
  {
    browserTable.clear();
    fetchProducts();
    if(credsModel.getLoggedUser() == null)
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
}
