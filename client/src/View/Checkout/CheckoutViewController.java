package View.Checkout;

import Core.ViewHandler;
import Core.ViewModelFactory;
import Model.Models.Order;
import Model.Models.Product;
import View.ViewController;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class CheckoutViewController extends ViewController
{
  private ViewHandler viewHandler;
  private CheckoutViewModel viewModel;

  //cart-products Table
  public ComboBox<String> filterByComboBox;
  public TableView<Product> cartProductsTable;
  public TableColumn<Product, String> productNameCol;
  public TableColumn<Product, Integer> productQuantityCol;
  public TableColumn<Product, Double> pricePerPieceCol;
  public TableColumn<Product, Double> totalPriceCol;
  public TableColumn<Product, String> productDescriptionCol;

  //orders Table

  public TableView<Order> ordersTable;
  public TableColumn<Order, Integer> orderIdCol;
  public TableColumn<Order, Double> orderTotalPrice;
  public TableColumn<Order, String> timestampCol;
  public TableColumn<Order, String> orderStateCol;

  //order-products Table
  public TableView<Product> orderProductsTable;
  public TableColumn<Product, String> productNameCol1;
  public TableColumn<Product, Integer> productQuantityCol1;
  public TableColumn<Product, Double> pricePerPieceCol1;

  public Label orderDetailLabel;

  public void init()
  {
    this.viewHandler = ViewHandler.getInstance();
    this.viewModel = ViewModelFactory.getCheckoutViewModel();
    viewModel.load();

    //cart-products Table
    cartProductsTable.setItems(viewModel.getCartProducts());
    viewModel.fetchCart();
    productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    productQuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    pricePerPieceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    totalPriceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
    productDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));


    //orders table
    ordersTable.setItems(viewModel.getOrders());
    ordersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> viewModel.setSelectedOrder(newValue));
    orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
    orderTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
    timestampCol.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
    orderStateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
    filterByComboBox.getItems().addAll("All orders", "Waiting", "In process", "Ready", "Canceled", "Picked up");
    filterByComboBox.valueProperty().bindBidirectional(viewModel.getStatusProperty());

    //order-products table
    orderDetailLabel.textProperty().bindBidirectional(viewModel.getDetailLabelProperty());
    orderProductsTable.setItems(viewModel.getOrderProducts());
    productNameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
    productQuantityCol1.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    pricePerPieceCol1.setCellValueFactory(new PropertyValueFactory<>("price"));
  }

  @Override public void beforeExitAction()
  {
    viewModel.remoProdCartWhenClose();
  }

  public void orderBtn(ActionEvent actionEvent)
  {
    viewModel.processOrder();
  }

  public void continueShoppingBtn(ActionEvent actionEvent)
  {
    viewModel.end();
    viewHandler.openBrowserPane();
  }

  public void onClick(MouseEvent mouseEvent)
  {
    if (mouseEvent.getClickCount() == 1 && !mouseEvent.isConsumed())
    {
      mouseEvent.consume();
      Order tempOrder = ordersTable.getSelectionModel().getSelectedItem();
      if (tempOrder != null)
        viewModel.showOrderDetails(tempOrder.getOrderId());
    }
  }

  public void deleteProdCart(ActionEvent event)
  {
    Product tempProduct = cartProductsTable.getSelectionModel().getSelectedItem();
   if (tempProduct != null)
   {
     int quantityProd = tempProduct.getQuantity();
     viewModel.removeFromCart(tempProduct, quantityProd);
   }
   else
   {
     createAlert(Alert.AlertType.INFORMATION, "no product to remove ...").showAndWait();
   }
  }

  public void filterBy(ActionEvent event)
  {
    viewModel.filterBy(filterByComboBox.valueProperty().get());
  }

  public void cancelOrder(ActionEvent event)
  {
    Order tempOrder = ordersTable.getSelectionModel().getSelectedItem();
    if (tempOrder != null && tempOrder.getState().equalsIgnoreCase("waiting"))
    {
      viewModel.cancelOrder(tempOrder);
    }
    else if (tempOrder == null)
    {
      createAlert(Alert.AlertType.INFORMATION, "no order to cancel...").showAndWait();
    }
    else
    {
      createAlert(Alert.AlertType.INFORMATION, "Can't cancel order ...").showAndWait();
    }
  }

  public void pickUpBtnPressed(ActionEvent actionEvent)
  {
    viewModel.pickUp(ordersTable.getSelectionModel().getSelectedItem());
  }


  private Alert createAlert(Alert.AlertType alertType, String alertMsg){
    Alert alert = new Alert(alertType);
    alert.setTitle(alertType.toString());
    alert.setHeaderText(alertMsg);
    alert.setContentText("");

    return alert;
  }
}
