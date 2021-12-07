package View.Checkout;

import Model.CredentialsModel;
import Model.CustomerModel;
import Model.Models.Order;
import Model.Models.Product;

import javafx.beans.property.*;
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
  private ObjectProperty<String> status;

  public CheckoutViewModel(CustomerModel customerModel, CredentialsModel credentialsModel)
  {
    this.customerModel = customerModel;
    this.credentialsModel=credentialsModel;

    cartProducts = FXCollections.observableArrayList();
    cartProducts.addAll(customerModel.getCartProducts());

    orders = FXCollections.observableArrayList();
    orders.addAll(customerModel.fetchCustomerOrders());
    orderProducts = FXCollections.observableArrayList();

    status = new SimpleObjectProperty<>();

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

  public ObjectProperty<String> getStatusProperty()
  {
    return status;
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
    cartProducts.clear();
    cartProducts.setAll(customerModel.getCartProducts());
  }

  public void removeFromCart(Product p,int quantityProd)
  {
    if (quantityProd<0)
    {
      ArrayList<Product> tempProducts = customerModel.getProducts();
      Product selectProd = null;
      for (Product tempProduct : tempProducts)
      {
        if (tempProduct.getProductId() == p.getProductId())
          selectProd = tempProduct;
      }
      customerModel.removeFromCart(selectProd, quantityProd);
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
    if (status.getValue() != null && stat.equals("All orders"))
    {
      orders.setAll(customerModel.fetchCustomerOrders());
    }
    else if (status.getValue() != null)
    {
      orders.clear();
      for (int i = 0; i < customerModel.fetchCustomerOrders().size(); i++)
      {
        if (customerModel.fetchCustomerOrders().get(i).getState().equals(stat))
          orders.add(customerModel.fetchCustomerOrders().get(i));
      }
    }
  }
}
