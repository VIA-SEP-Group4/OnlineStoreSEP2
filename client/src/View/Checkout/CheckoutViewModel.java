package View.Checkout;

import Model.CredentialsModel;
import Model.CustomerModel;
import Model.Models.Order;
import Model.Models.Product;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;

public class CheckoutViewModel
{
  private CustomerModel customerModel;
  private CredentialsModel credentialsModel;
  private ObservableList<Product> cartProducts;
  private ObservableList<Order> orders;
  private ObservableList<Product> orderProducts;
  private StringProperty orderDetailLabel;

  public CheckoutViewModel(CustomerModel customerModel, CredentialsModel credentialsModel)
  {
    this.customerModel = customerModel;
    this.credentialsModel=credentialsModel;

    cartProducts = FXCollections.observableArrayList();
    cartProducts.addAll(customerModel.getCartProducts());

    orders = FXCollections.observableArrayList();
    orders.addAll(customerModel.fetchCustomerOrders());
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


  public void processOrder()
  {
    if (!cartProducts.isEmpty()){
      ArrayList<Product> tempProducts = new ArrayList<>(cartProducts);
      Order newOrder = new Order(credentialsModel.getLoggedCustomer().getCustomerId(), tempProducts);

      orders.add(newOrder);
      customerModel.processOrder(newOrder);

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
    cartProducts.setAll(customerModel.getCartProducts());
  }
}
