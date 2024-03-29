package View.Checkout;

import Model.CredentialsModel;
import Model.Models.Order;
import Model.Models.Product;
import Model.OrdersModel;
import Model.ProductsModel;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Optional;

public class CheckoutViewModel implements PropertyChangeListener
{
  private ProductsModel productsModel;
  private CredentialsModel credentialsModel;
  private OrdersModel ordersModel;
  private ObservableList<Product> cartProducts;
  private ObservableList<Order> orders;
  private ObjectProperty<Order> selectedOrder;
  private ObservableList<Product> orderProducts;
  private StringProperty orderDetailLabel;
  private ObjectProperty<String> status;

  public CheckoutViewModel(ProductsModel productsModel, CredentialsModel credentialsModel, OrdersModel ordersModel)
  {
    this.productsModel = productsModel;
    this.credentialsModel = credentialsModel;
    this.ordersModel = ordersModel;

    cartProducts = FXCollections.observableArrayList();
    orders = FXCollections.observableArrayList();
    orderProducts = FXCollections.observableArrayList();

    selectedOrder = new SimpleObjectProperty<>();
    status = new SimpleObjectProperty<>();
    orderDetailLabel = new SimpleStringProperty();
  }

  public void load()
  {
    cartProducts.setAll(credentialsModel.getLoggedCustomer().getCart());
    orders.setAll(ordersModel.getCustomerOrders(credentialsModel.getLoggedCustomer().getCustomerId()));

    activateListeners();
  }
  public void end(){
    cartProducts.clear();
    orders.clear();
    deactivateListeners();
  }

  private void activateListeners()
  {
    ordersModel.addListener("newOrder", this);
    ordersModel.addListener("updatedOrderStatus", this);
    ordersModel.activateListeners();
  }
  private void deactivateListeners()
  {
    ordersModel.removeListener("newOrder", this);
    ordersModel.removeListener("updatedOrderStatus", this);
    ordersModel.deactivateListeners();
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
      Alert alert = createAlert(Alert.AlertType.CONFIRMATION, "Do you want to complete the order?");

      Optional<ButtonType> res = alert.showAndWait();
      if(res.isPresent() && res.get().equals(ButtonType.OK))
      {

        Order newOrder = new Order(
            credentialsModel.getLoggedCustomer().getCustomerId(), new ArrayList<>(cartProducts));
        ordersModel.processOrder(newOrder);

        //clear curr/loaded table
        cartProducts.clear();
        //clear logged customer's cart/product list !!!
        credentialsModel.getLoggedCustomer().getCart().clear();
        createAlert(Alert.AlertType.INFORMATION,"The order has been placed!").showAndWait();
      }
    }
    else {
      Alert alert = createAlert(Alert.AlertType.INFORMATION, "Nothing to order ...\n Choose some products first.");
      Platform.runLater(()->{alert.showAndWait();});
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

  public void fetchCart()
  {
    cartProducts.setAll(credentialsModel.getLoggedCustomer().getCart());
  }

  public void removeFromCart(Product p, int quantityProd)
  {
    productsModel.removeFromCart(p, quantityProd, credentialsModel.getLoggedCustomer());
    fetchCart();
  }
  public boolean logOutCustomer()
  {
    if(credentialsModel.getLoggedCustomer() != null && !credentialsModel.getLoggedCustomer().getCart().isEmpty())
    {
      Alert alert = createAlert(Alert.AlertType.CONFIRMATION, "If you log out, you will lose your selected products");

      Optional<ButtonType> res = alert.showAndWait();
      if(res.isPresent() && res.get().equals(ButtonType.OK)) {
        remoProdCartWhenClose();
        credentialsModel.logOutCustomer();
        return true;
      }
      else
        return false;
    }

    else
    {
      credentialsModel.logOutCustomer();
      return true;
    }
  }

  public void remoProdCartWhenClose()
  {
    if (credentialsModel.getLoggedCustomer() != null)
    {
      ArrayList<Product> tempProducts = credentialsModel.getLoggedCustomer().getCart();
      for (Product tempProduct : tempProducts)
      {
        productsModel.removeFromCart(tempProduct, tempProduct.getQuantity(),
            credentialsModel.getLoggedCustomer());
      }
    }
  }

  public void filterBy(String stat)
  {
    orders.clear();
    if (status.getValue() != null && !status.getValue().equals("All orders"))
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

  public void cancelOrder(Order selectedOrder)
  {
    if (selectedOrder != null && selectedOrder.getState().equalsIgnoreCase("waiting"))
    {
      if (createAlert(Alert.AlertType.CONFIRMATION,
          "Do you want to cancel the order?").showAndWait().get() == ButtonType.OK)
      {
        for (int i = 0; i < selectedOrder.getProducts().size(); i++)
        {
          productsModel.addProdToStock(selectedOrder.getProducts().get(i),
              selectedOrder.getProducts().get(i).getQuantity());
        }
        ordersModel.updateOrderState(selectedOrder, "cancelled");
      }
    }
    else if (selectedOrder == null)
    {
      createAlert(Alert.AlertType.ERROR, "no order to cancel").showAndWait();
    }
    else
    {
      createAlert(Alert.AlertType.INFORMATION, "Your Order n."+selectedOrder.getOrderId()+ " can't be cancelled").showAndWait();
    }
  }

  public void pickUp(Order selectedOrder)
  {
    String reply;

    if (selectedOrder.getState().equalsIgnoreCase("ready")){
      ordersModel.updateOrderState(selectedOrder, "picked");
      reply = "Your Order n."+selectedOrder.getOrderId()+ " was picked! Enjoy:)";
    }
    else {
      reply = "You cannot pick your order up yet!\n its state must be 'ready' to be available for pick-up.";
    }

    Alert alert = createAlert(Alert.AlertType.INFORMATION, reply);
    Platform.runLater(()->{alert.showAndWait();});
  }


  private Alert createAlert(Alert.AlertType alertType, String alertMsg){
    Alert alert = new Alert(alertType);
    alert.setTitle(alertType.toString());
    alert.setHeaderText(alertMsg);
    alert.setContentText("");

    return alert;
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    if (evt.getPropertyName().equals("newOrder"))
      addOrder((Order) evt.getNewValue());

    else if (evt.getPropertyName().equals("updatedOrderStatus"))
      updateOrders((Order) evt.getNewValue());

    orders.clear();
    orders.setAll(credentialsModel.getLoggedCustomer().getOrders());
  }

  private void addOrder(Order newOrder)
  {
    credentialsModel.getLoggedCustomer().getOrders().add((newOrder));
  }

  private void updateOrders(Order updatedOrder)
  {
    credentialsModel.getLoggedCustomer().removeFromOrders(updatedOrder.getOrderId());
    credentialsModel.getLoggedCustomer().getOrders().add(updatedOrder);
  }
}
