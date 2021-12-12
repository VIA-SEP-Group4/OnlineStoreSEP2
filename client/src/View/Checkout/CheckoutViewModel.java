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
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

public class CheckoutViewModel
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
    ordersModel.addListener("newOrder", this::addOrder);
    ordersModel.addListener("updatedOrderStatus", this::updateOrders);
    ordersModel.activateListeners();
  }
  private void deactivateListeners()
  {
    ordersModel.removeListener("newOrder", this::addOrder);
    ordersModel.removeListener("updatedOrderStatus", this::updateOrders);
    ordersModel.deactivateListeners();
  }

  private void addOrder(PropertyChangeEvent evt)
  {
    orders.add((Order) evt.getNewValue());
  }

  private void updateOrders(PropertyChangeEvent evt)
  {
    Order order= (Order) evt.getNewValue();

    for (Order o : orders){
      if (o.getOrderId() == order.getOrderId()){
        orders.remove(o);
        orders.add(order);
        break;
      }
    }
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
      Order newOrder = new Order(credentialsModel.getLoggedCustomer().getCustomerId(), new ArrayList<>(cartProducts));
      ordersModel.processOrder(newOrder);

      //clear curr/loaded table
      cartProducts.clear();
      //clear logged customer's cart/product list !!!
      credentialsModel.getLoggedCustomer().getCart().clear();
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

  public void cancelOrder(Order order)
  {
    for (int i = 0; i < order.getProducts().size(); i++)
    {
      productsModel.addProdToStock(order.getProducts().get(i),order.getProducts().get(i).getQuantity());
    }
    ordersModel.cancelOrder(order, "Cancelled");
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

}
