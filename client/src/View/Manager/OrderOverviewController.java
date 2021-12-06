package View.Manager;

import Core.ViewModelFactory;
import Model.Models.Employee;
import Model.Models.Order;
import Model.Models.Product;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;

import java.awt.event.MouseEvent;

public class OrderOverviewController {
    public TableView<Order> ordersTable;
    public TableColumn<Order, Integer> orderIdCol;
    public TableColumn<Order, String> statusCol;
    public TableColumn<Order, String> timeCol;
    public TableColumn<Order, Integer> customerIDCol;
    public TableColumn<Order, Integer> workerIdCol;
    public TableView<Product> orderedProducts;
    public TableColumn<Product, Integer> productIdCol;
    public TableColumn<Product, String> nameProdCol;
    public TableColumn<Product, String> typeProdCol;
    public TableColumn<Product, Integer> priceCol;
    public TableColumn<Product, Integer> quantityCol;
    private OrderOverviewViewModel viewModel;

    public void init() {
        this.viewModel = ViewModelFactory.getOrdersOverviewViewModel();
        ordersTable.setItems(viewModel.getAllOrders());
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("state"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        workerIdCol.setCellValueFactory(new PropertyValueFactory<>("wwId"));
        orderedProducts.setItems(viewModel.getProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        nameProdCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeProdCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    public void onSelectOrder(javafx.scene.input.MouseEvent mouseEvent) {
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY) &&  mouseEvent.getClickCount()==2){
            Order ord=ordersTable.getSelectionModel().getSelectedItem();
            if(ord!=null )
                viewModel.setProducts(ord);
        }
    }
}
