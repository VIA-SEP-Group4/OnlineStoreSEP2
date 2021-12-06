package View.Browser;

import Model.CredentialsModel;
import Model.CustomerModel;
import Model.Models.Product;
import View.Manager.TableProdViewModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class BrowserViewModel implements PropertyChangeListener
{
  private CustomerModel customerModel;
  private CredentialsModel credsModel;
  private StringProperty search;
  private StringProperty items;
  private StringProperty userName;

  private ObservableList<Product> browserTable;
  private ObjectProperty<TableProdViewModel> selectedProd;

  private BooleanProperty logOut;
  private BooleanProperty logIn;

  private ArrayList<Product> cart;

  public BrowserViewModel(CustomerModel customerModel, CredentialsModel credsModel)
  {
    this.customerModel = customerModel;
    search = new SimpleStringProperty("");
    items = new SimpleStringProperty("");
    userName = new SimpleStringProperty();
    this.credsModel=credsModel;
    browserTable = FXCollections.observableArrayList();
    selectedProd = new SimpleObjectProperty<>();

    logOut = new SimpleBooleanProperty(true);
    logIn = new SimpleBooleanProperty(false);

    cart = new ArrayList<>();
    customerModel.addListener("ProductsReply",this);
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
    browserTable.addAll(customerModel.getProducts());
  }

  public void addBasket()
  {
     ArrayList<Product> basket = customerModel.getCartProducts();

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
          customerModel.addToCart(prod);
      }
      items.setValue("("+itemQuantity()+") items");
  }

    public boolean hasProducts()
    {
      ArrayList<Product> basket = customerModel.getCartProducts();
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
    for (int i = 0; i < customerModel.getCartProducts().size(); i++)
    {
      iQ += customerModel.getCartProducts().get(i).getQuantity();
    }
    return iQ;
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
      customerModel.addToCart(p, desiredQuantity);
      reset();
    }
    else
    {
      //TODO .. put it in some label so customer can see what's going on
      System.out.println("error label ->wrong quantity ...");
    }
  }
}
