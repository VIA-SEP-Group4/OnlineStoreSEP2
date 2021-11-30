package View.Checkout;

import Model.Order;
import Model.Product;
import Model.ProductsModel;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventTarget;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class CheckoutViewModel
{
  private ProductsModel productsModelManager;
  private ObservableList<Product> cartProducts;
  private ObservableList<Order> orders;
  private ObservableList<Product> orderProducts;
  private StringProperty orderDetailLabel;

  public CheckoutViewModel(ProductsModel productsModelManager)
  {
    this.productsModelManager = productsModelManager;

    cartProducts = FXCollections.observableArrayList();
    cartProducts.add(new Product("product", "type", 12.99, "dscrpt", 2));

    orders = FXCollections.observableArrayList();
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
      orders.add(new Order(serial.getAndIncrement(), tempProducts));
      //TODO ... create new Order and send to DB ???
      //productsModelManager.processOrder()

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
}
