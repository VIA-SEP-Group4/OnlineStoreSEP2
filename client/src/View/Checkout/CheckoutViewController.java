package View.Checkout;

import Core.ViewHandler;
import Core.ViewModelFactory;
import Model.Order;
import Model.Product;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.sql.Timestamp;

public class CheckoutViewController
{
  private ViewHandler viewHandler;
  private CheckoutViewModel viewModel;

  //cart-products Table
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
  public TableColumn<Order, Timestamp> timestampCol;
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

    //cart-products Table
    cartProductsTable.setItems(viewModel.getCartProducts());
    productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    productQuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    pricePerPieceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    totalPriceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
    productDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

    //orders table
    ordersTable.setItems(viewModel.getOrders());
    orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
    orderTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
    timestampCol.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
    orderStateCol.setCellValueFactory(new PropertyValueFactory<>("state"));

    //order-products table
    orderDetailLabel.textProperty().bindBidirectional(viewModel.getDetailLabelProperty());
    orderProductsTable.setItems(viewModel.getOrderProducts());
    productNameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
    productQuantityCol1.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    pricePerPieceCol1.setCellValueFactory(new PropertyValueFactory<>("price"));

    //fetch existing orders
  }

  public void orderBtn(ActionEvent actionEvent)
  {
    viewModel.processOrder();
  }

  public void continueShoppingBtn(ActionEvent actionEvent)
  {
    viewHandler.openBrowserPane();
  }

  public void checkOrderedProductDetail(MouseEvent mouseEvent)
  {
    if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2){
      Order tempOrder = ordersTable.getSelectionModel().getSelectedItem();
      if (tempOrder != null)
        viewModel.showOrderDetails(tempOrder.getOrderId());
    }
    ordersTable.getSelectionModel().clearSelection();
  }
}
