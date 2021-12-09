package View.Checkout;

import Model.CredentialsModel;
import Model.Models.Order;
import Model.Models.Product;
import Model.OrdersModel;
import Model.ProductsModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

public class CheckoutViewModel
{
  private ProductsModel productsModel;
  private CredentialsModel credentialsModel;
  private OrdersModel ordersModel;
  private ObservableList<Product> cartProducts;
  private ObservableList<Order> orders;
  private ObservableList<Product> orderProducts;
  private StringProperty orderDetailLabel;
  private ObjectProperty<String> status;

  public CheckoutViewModel(ProductsModel productsModel, CredentialsModel credentialsModel, OrdersModel ordersModel)
  {
    this.productsModel = productsModel;
    this.credentialsModel=credentialsModel;
    this.ordersModel=ordersModel;

    cartProducts = FXCollections.observableArrayList();
    cartProducts.setAll(productsModel.getCartProducts());

    orders = FXCollections.observableArrayList();
    orders.setAll(ordersModel.getCustomerOrders(credentialsModel.getLoggedCustomer().getCustomerId()));
    orderProducts = FXCollections.observableArrayList();

    status = new SimpleObjectProperty<>();

    orderDetailLabel = new SimpleStringProperty();

    ordersModel.addListener("newOrder", this::updateOrders);
  }

  private void updateOrders(PropertyChangeEvent evt)
  {

    orders.setAll((ArrayList<Order>)evt.getNewValue());
  }

  public ObservableList<Order> getOrders()
  {
    return orders;
  }

  public ObservableList<Product> getCartProducts()
  {
    return cartProducts;
  }

  public ObservableList<Product> getOrderProducts()
  {
    return orderProducts;
  }

  public ObjectProperty<String> getStatusProperty()
  {
    return status;
  }

  public void processOrder()
  {
    if (!cartProducts.isEmpty()){
      ArrayList<Product> tempProducts = new ArrayList<>(cartProducts);
      Order newOrder = new Order(credentialsModel.getLoggedCustomer().getCustomerId(), tempProducts);

//      orders.add(newOrder);
      ordersModel.processOrder(newOrder);

      cartProducts.clear();
    }
    else {
      System.out.println("Nothing to order ...");
      //ToDO ...show label instead
    }
  }

  public void showOrderDetails(int orderId){
    orderProducts.clear();
    for (Order o : orders){
      if (o.getOrderId() == orderId)
        orderProducts.addAll(o.getProducts());
    }
    orderDetailLabel.setValue("Order n." + orderId + " detail:");
  }

  public Property<String> getDetailLabelProperty()
  {
    return orderDetailLabel;
  }

  public void fetchCart()
  {
    cartProducts.clear();
    cartProducts.setAll(productsModel.getCartProducts());
  }

  public void removeFromCart(Product p,int quantityProd)
  {
    if (quantityProd<0)
    {
      ArrayList<Product> tempProducts = productsModel.getAllProducts();
      Product selectProd = null;
      for (Product tempProduct : tempProducts)
      {
        if (tempProduct.getProductId() == p.getProductId())
          selectProd = tempProduct;
      }
      productsModel.removeFromCart(selectProd, quantityProd);
      fetchCart();
    }
    else
    {
      //TODO .. put it in some label so customer can see what's going on
      System.out.println("error label ->no product to remove ...");
    }
  }

  public void filterBy(String stat)
  {
    orders.clear();

    if (status.getValue() != null)
    {
      for (Order o : ordersModel.getCustomerOrders(credentialsModel.getLoggedCustomer().getCustomerId())){
        if (o.getState().equalsIgnoreCase(stat))
          orders.add(o);
      }
    }
    else
    {
      orders.setAll(ordersModel.getCustomerOrders(credentialsModel.getLoggedCustomer().getCustomerId()));
    }
  }
}
