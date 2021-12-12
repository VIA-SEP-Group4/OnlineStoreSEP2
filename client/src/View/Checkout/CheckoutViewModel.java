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
    cartProducts.setAll(credentialsModel.getLoggedCustomer().getCart());

    orders = FXCollections.observableArrayList();
    orders.setAll(ordersModel.getCustomerOrders(credentialsModel.getLoggedCustomer().getCustomerId()));
    orderProducts = FXCollections.observableArrayList();

    selectedOrder = new SimpleObjectProperty<>();
    status = new SimpleObjectProperty<>();
    orderDetailLabel = new SimpleStringProperty();

    ordersModel.addListener("newOrder", this::updateOrders);
    ordersModel.addListener("updatedOrderStatus", this::updateOrders);
  }

  private void updateOrders(PropertyChangeEvent evt)
  {
    Order order= (Order) evt.getNewValue();
    boolean found=false;
    for(int i=0;i<orders.size();i++){
      if(orders.get(i).getOrderId()== order.getOrderId()){
        orders.set(i,order);
        found=true;
        break;
      }
    }
    if(!found){
      orders.add(order);
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

  public void fetchCart()
  {
    cartProducts.setAll(credentialsModel.getLoggedCustomer().getCart());
  }

  public void removeFromCart(Product p, int quantityProd)
  {
    productsModel.removeFromCart(p, quantityProd, credentialsModel.getLoggedCustomer());
    fetchCart();
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
