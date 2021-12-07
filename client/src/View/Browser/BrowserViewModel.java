package View.Browser;

import Model.CredentialsModel;
import Model.CustomerModel;
import Model.Models.Product;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class BrowserViewModel implements PropertyChangeListener
{
  private CustomerModel customerModel;
  private CredentialsModel credsModel;
  private StringProperty search;
  private StringProperty items;
  private StringProperty userName;

  private ObservableList<String> typeList;
  private ObjectProperty<String> type;
  private ObservableList<Product> browserTable;
  private ObjectProperty<Product> selectedProd;

  private BooleanProperty logOut;
  private BooleanProperty logIn;


  public BrowserViewModel(CustomerModel customerModel, CredentialsModel credsModel)
  {
    this.customerModel = customerModel;
    search = new SimpleStringProperty("");
    items = new SimpleStringProperty("");
    userName = new SimpleStringProperty();

    typeList = FXCollections.observableArrayList();
    type = new SimpleObjectProperty();

    this.credsModel=credsModel;
    browserTable = FXCollections.observableArrayList();
    selectedProd = new SimpleObjectProperty<>();

    logOut = new SimpleBooleanProperty(true);
    logIn = new SimpleBooleanProperty(false);

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

  public void setLogIn(boolean logIn)
  {
    this.logIn.set(logIn);
  }

  public void setLogOut(boolean logOut)
  {
    this.logOut.set(logOut);
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
    browserTable.addAll(customerModel.getProducts());
  }

  public void reset()
  {
    browserTable.clear();
    fetchProducts();
    loadTypes();
    if(credsModel.getLoggedCustomer() == null)
    {
      logOut.setValue(true);
      logIn.setValue(false);
      userName.setValue("");
    }
    else {
      logOut.setValue(false);
      logIn.setValue(true);
      userName.setValue("Hello, "+credsModel.getLoggedCustomer().getLastName());

    }
  }

  public void getSearch()
  {
    browserTable.clear();
    browserTable.addAll(searchList(search.getValue(),customerModel.getProducts()));
  }

  private ArrayList<Product> searchList(String searchWord, ArrayList<Product> listOfProducts)
  {
    List<String> searchWordArray = Arrays.asList(searchWord.trim().split(""));

    return (ArrayList<Product>) listOfProducts.stream().filter(input -> {
      return searchWordArray.stream().allMatch(word ->
          input.getDescription().toLowerCase().contains(word.toLowerCase()));
    }).collect(Collectors.toList());
  }

  private void loadTypes()
  {
    typeList.clear();
    typeList.add("All products");
    typeList.add("Available");
    for (int i = 0; i < browserTable.size(); i++)
    {
      if(!typeList.contains(getBrowserTable().get(i).getType()))
        typeList.add(getBrowserTable().get(i).getType());
    }
  }

  public void filterBy()
  {
    if (type.getValue() != null && type.getValue().equals("Available"))
    {
      browserTable.clear();
      for (int i = 0; i < customerModel.getProducts().size(); i++)
      {
        if (customerModel.getProducts().get(i).getQuantity() > 0)
          browserTable.add(customerModel.getProducts().get(i));
      }
    }
    else if (type.getValue() != null && type.getValue().equals("All products"))
    {
      browserTable.clear();
      browserTable.addAll(customerModel.getProducts());
    }
    else if (type.getValue() != null)
    {
    browserTable.clear();
    for (int i = 0; i < customerModel.getProducts().size(); i++)
    {
      if (customerModel.getProducts().get(i).getType().equals(type.getValue()))
        browserTable.add(customerModel.getProducts().get(i));
    }
    }
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    ArrayList<Product> products = (ArrayList<Product>) evt.getNewValue();
    browserTable.setAll(products);
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

  public void setType(String type)
  {
    this.type.set(type);
  }

  public ObjectProperty<String> getTypeProperty()
  {
    return type;
  }

  public ObservableList<String> getAllTypes()
  {
    return typeList;
  }
}
