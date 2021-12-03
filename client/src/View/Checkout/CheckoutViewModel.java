package View.Checkout;

import Model.CredentialsModel;
import Model.Order;
import Model.Product;
import Model.ProductsModel;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class CheckoutViewModel
{
  private ProductsModel productsModelManager;
  private CredentialsModel credentialsModel;
  private ObservableList<Product> cartProducts;
  private ObservableList<Order> orders;
  private ObservableList<Product> orderProducts;
  private StringProperty orderDetailLabel;

  public CheckoutViewModel(ProductsModel productsModelManager,CredentialsModel credentialsModel)
  {
    this.productsModelManager = productsModelManager;
    this.credentialsModel=credentialsModel;

    cartProducts = FXCollections.observableArrayList();
    cartProducts.addAll(productsModelManager.getCartProducts());

    orders = FXCollections.observableArrayList();
    orders.addAll(productsModelManager.fetchOrders());
    orderProducts = FXCollections.observableArrayList();

    orderDetailLabel = new SimpleStringProperty();
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

  private static final AtomicInteger serial = new AtomicInteger();
  public void processOrder()
  {
    if (!cartProducts.isEmpty()){
      ArrayList<Product> tempProducts = new ArrayList<>(cartProducts);
      Order newOrder = new Order(serial.getAndIncrement(), credentialsModel.getLoggedCustomer().getUserId(), tempProducts);

      orders.add(newOrder);
      productsModelManager.processOrder(newOrder);

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
    cartProducts.setAll(productsModelManager.getCartProducts());
  }
}
