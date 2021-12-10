package View.Checkout;

import Model.CredentialsModel;
import Model.CustomerModel;
import Model.Models.Order;
import Model.Models.Product;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

public class CheckoutViewModel
{
  private CustomerModel customerModel;
  private CredentialsModel credentialsModel;
  private ObservableList<Product> cartProducts;
  private ObservableList<Order> orders;
  private ObjectProperty<Order> selectedOrder;
  private ObservableList<Product> orderProducts;
  private StringProperty orderDetailLabel;
  private ObjectProperty<String> status;

  public CheckoutViewModel(CustomerModel customerModel, CredentialsModel credentialsModel)
  {
    this.customerModel = customerModel;
    this.credentialsModel = credentialsModel;

    cartProducts = FXCollections.observableArrayList();
    cartProducts.setAll(customerModel.getCartProducts());

    orders = FXCollections.observableArrayList();
    orders.setAll(customerModel.fetchCustomerOrders());
    orderProducts = FXCollections.observableArrayList();
    selectedOrder = new SimpleObjectProperty<>();

    status = new SimpleObjectProperty<>();

    orderDetailLabel = new SimpleStringProperty();

    customerModel.addListener("newOrder", this::updateOrders);
  }

  private void updateOrders(PropertyChangeEvent evt)
  {
    orders.setAll((ArrayList<Order>) evt.getNewValue());
  }

  public Order getSelectedOrder()
  {
    return selectedOrder.get();
  }

  public ObjectProperty<Order> selectedOrderProperty()
  {
    return selectedOrder;
  }

  public void setSelectedOrder(Order selectedOrder)
  {
    this.selectedOrder.set(selectedOrder);
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

  public Property<String> getDetailLabelProperty()
  {
    return orderDetailLabel;
  }

  public void processOrder()
  {
    if (!cartProducts.isEmpty())
    {
      ArrayList<Product> tempProducts = new ArrayList<>(cartProducts);
      Order newOrder = new Order(
          credentialsModel.getLoggedCustomer().getCustomerId(), tempProducts);

      customerModel.processOrder(newOrder);
      cartProducts.clear();
    }
    else
    {
      System.out.println("Nothing to order ...");
      //ToDO ...show label instead
    }
  }

  public void showOrderDetails(int orderId)
  {
    orderProducts.clear();
    for (Order o : orders)
    {
      if (o.getOrderId() == orderId)
        orderProducts.addAll(o.getProducts());
    }
    orderDetailLabel.setValue("Order n." + orderId + " detail:");
  }

  public void fetchCart()
  {
    cartProducts.clear();
    cartProducts.setAll(customerModel.getCartProducts());
  }

  public void removeFromCart(Product p, int quantityProd)
  {
      customerModel.removeFromCart(p, quantityProd);
      fetchCart();
  }

  public void filterBy(String stat)
  {
    orders.clear();

    if (status.getValue() != null)
    {
      for (Order o : customerModel.fetchCustomerOrders())
      {
        if (o.getState().equalsIgnoreCase(stat))
          orders.add(o);
      }
    }
    else
    {
      orders.setAll(customerModel.fetchCustomerOrders());
    }
  }

  public void cancelOrder(Order order)
  {
    customerModel.cancelOrder(order, "Canceled");
  }
}
