package View.Browser;

import Model.CredentialsModel;
import Model.Models.Product;
import Model.ProductsModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BrowserViewModel implements PropertyChangeListener
{
  private ProductsModel productsModel;
  private CredentialsModel credentialsModel;
  private StringProperty search;
  private StringProperty items;
  private StringProperty userName;
  private IntegerProperty page;

  private ObjectProperty<Integer> pagQuant;
  private ObservableList<String> typeList;
  private ObjectProperty<String> type;
  private ObservableList<Product> browserTable;
  private ObjectProperty<Product> selectedProd;

  private BooleanProperty logOut;
  private BooleanProperty logIn;


  public BrowserViewModel(ProductsModel productsModel, CredentialsModel credsModel)
  {
    this.productsModel = productsModel;
    search = new SimpleStringProperty("");
    items = new SimpleStringProperty("");
    userName = new SimpleStringProperty();
    page = new SimpleIntegerProperty();
    pagQuant = new SimpleObjectProperty<>();

    typeList = FXCollections.observableArrayList();
    type = new SimpleObjectProperty();

    this.credentialsModel =credsModel;
    browserTable = FXCollections.observableArrayList();
    selectedProd = new SimpleObjectProperty<>();

    logOut = new SimpleBooleanProperty(true);
    logIn = new SimpleBooleanProperty(false);

    productsModel.addListener("ProductsReply",this);
    productsModel.addListener("productDeleted",this::deletedProduct);
  }

  private void deletedProduct(PropertyChangeEvent propertyChangeEvent) {
    Product prod=(Product) propertyChangeEvent.getNewValue();
    for(Product p: browserTable){
      if(p.getProductId()==prod.getProductId()){
        browserTable.remove(p);
        break;
      }
    }
  }

  public IntegerProperty pageProperty()
  {
    return page;
  }

  public ObjectProperty<Integer> pagQuantProperty()
  {
    return pagQuant;
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

  public void setSelectedProd(Product selectedProd)
  {
    this.selectedProd.set(selectedProd);
  }

  public Product getSelectedProd()
  {
    return selectedProd.get();
  }

  public void fetchProducts(){
    browserTable.setAll(productsModel.getProducts(page.getValue(),pagQuant.getValue()));
  }

  public void reset()
  {
    fetchProducts();
    itemQuantity();
    loadTypes();
    if(credentialsModel.getLoggedCustomer() == null)
    {
      logOut.setValue(true);
      logIn.setValue(false);
      userName.setValue("");
    }
    else {
      logOut.setValue(false);
      logIn.setValue(true);
      userName.setValue("Hello, "+ credentialsModel.getLoggedCustomer().getFirstName());
    }
  }

  public void getSearch()
  {
    fetchProducts();
    browserTable.setAll(searchList(search.getValue(), new ArrayList<>(getBrowserTable())));
  }
  private ArrayList<Product> searchList(String searchWord, ArrayList<Product> listOfProducts)
  {
    List<String> searchWordArray = Arrays.asList(searchWord.trim().split(""));

    return (ArrayList<Product>) listOfProducts.stream().filter(input -> searchWordArray.stream().allMatch(word ->
        input.getDescription().toLowerCase().contains(word.toLowerCase()))).collect(Collectors.toList());
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
  private void itemQuantity()
  {
    if (credentialsModel.getLoggedCustomer()!= null)
    {
      int iQ = 0;
      for (Product cartProduct : credentialsModel.getLoggedCustomer().getCart())
      {
        iQ += cartProduct.getQuantity();
      }
      items.setValue("(" + iQ + ") items");
    }
  }

  public int setMaxPage()
  {
    if (pagQuant.getValue() == null)
    {
      assert pagQuant != null;
      pagQuant.setValue(10);
    }
    return (((productsModel.getProducts(0,1000).size())-1)/(pagQuant.getValue()))+1;
  }

  public void filterBy()
  {
    if (type.getValue() != null && type.getValue().equals("Available"))
    {
      fetchProducts();
      IntStream.range(0, browserTable.size())
          .filter(i -> browserTable.get(i).getQuantity() == 0)
          .forEach(i -> browserTable.remove(i));
    }
    else if (type.getValue() != null && type.getValue().equals("All products"))
    {
      fetchProducts();
    }
    else if (type.getValue() != null)
    {
      browserTable.clear();
      browserTable.addAll(productsModel.getFilterProd(page.getValue(),pagQuant.getValue(),type.getValue()));
    }
  }

  public void addToCart(Product p, int desiredQuantity)
  {
    if (desiredQuantity>0 && desiredQuantity<=p.getQuantity())
    {
      productsModel.addToCart(p, desiredQuantity, credentialsModel.getLoggedCustomer());
      reset();
    }
    else
    {
      createAlert(Alert.AlertType.ERROR, "Unable to add "+desiredQuantity+ "of product-" + p.getName()
          +"\nselect valid amount please.").showAndWait();
    }
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    Product product= (Product) evt.getNewValue();
    boolean found=false;
    for(int i=0;i<browserTable.size();i++){
      if(browserTable.get(i).getProductId()== product.getProductId()){
        browserTable.set(i,product);
        found=true;
        break;
      }
    }
    if(!found){
      browserTable.add(product);
    }
  }

  public void logOutCustomer()
  {
    credentialsModel.logOutCustomer();
  }


  private Alert createAlert(Alert.AlertType alertType, String alertMsg){
    Alert alert = new Alert(alertType);
    alert.setTitle(alertType.toString());
    alert.setHeaderText(alertMsg);
    alert.setContentText("");

    return alert;
  }
}