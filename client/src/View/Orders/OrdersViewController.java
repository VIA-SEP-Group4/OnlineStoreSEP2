package View.Orders;

import Core.ViewHandler;
import Core.ViewModelFactory;
import Model.Models.Order;
import Model.Models.Product;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class OrdersViewController
{
    private ViewHandler viewHandler;
    private OrdersViewModel viewModel;

    public TableView<Order> openOrdersTable;
    public TableColumn<Order, Integer> IDColumn;
    public TableColumn<Order, String> timeColumn;
    public TableColumn<Order, Double> totalPriceColumn;
    public TableColumn<Order, String> statusColumn;

    public TableView<Product> openDetailTable;
    public TableColumn<Product, String> nameColumn;
    public TableColumn<Product, String> typeColumn;
    public TableColumn<Product, Double> unitPriceColumn;
    public TableColumn<Product, Integer> quantityColumn;
    public TableColumn<Product, String> descriptionColumn;

    public TableView<Order> myOrdersTable;
    public TableColumn<Order, Integer> myIDColumn;
    public TableColumn<Order, String> myTimeColumn;
    public TableColumn<Order, Double> myTotalPriceColumn;
    public TableColumn<Order, String> myStatusColumn;

    public TableView<Product> myDetailTable;
    public TableColumn<Product, String> myNameColumn;
    public TableColumn<Product, String> myTypeColumn;
    public TableColumn<Product, Double> myUnitPriceColumn;
    public TableColumn<Product, Integer> myQuantityColumn;
    public TableColumn<Product, String> myDescriptionColumn;

    public void init(){
        this.viewHandler = ViewHandler.getInstance();
        this.viewModel = ViewModelFactory.getOrdersViewModel();

        viewModel.getOrders();
        openOrdersTable.setItems(viewModel.getOpenOrders());
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("state"));

        openDetailTable.setItems(viewModel.getOpenOrdersDetail());
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        myOrdersTable.setItems(viewModel.getMyOrders());
        myIDColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        myTotalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        myTimeColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        myStatusColumn.setCellValueFactory(new PropertyValueFactory<>("state"));

        myDetailTable.setItems(viewModel.getMyOrdersDetail());
        myNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        myTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        myQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        myUnitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        myDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    }


    public void pickOrderButton(ActionEvent actionEvent)
    {
        Order tempOrder = openOrdersTable.getSelectionModel().getSelectedItem();
        if(tempOrder != null){
            viewModel.changeOrderAssignee(tempOrder, false);
            viewModel.pickOrder(tempOrder.getOrderId());
        }
    }

    public void removeButton(ActionEvent actionEvent)
    {
        Order tempOrder = myOrdersTable.getSelectionModel().getSelectedItem();
        if (tempOrder != null){
            viewModel.changeOrderAssignee(tempOrder, true);
            viewModel.removeOrder(tempOrder.getOrderId());
        }
    }

    public void completeOrderButton(ActionEvent actionEvent)
    {
        Order tempOrder = myOrdersTable.getSelectionModel().getSelectedItem();
        if (tempOrder != null){
            viewModel.completeOrder(tempOrder.getOrderId());
        }
        myOrdersTable.refresh();
    }

    public void checkOpenOrderDetail(MouseEvent mouseEvent)
    {
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            Order tempOrder = openOrdersTable.getSelectionModel().getSelectedItem();
            if(tempOrder != null){
                viewModel.showOpenOrderDetails(tempOrder.getOrderId());
            }
        }
    }

    public void checkMyOrderDetail(MouseEvent mouseEvent)
    {
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            Order tempOrder = myOrdersTable.getSelectionModel().getSelectedItem();
            if(tempOrder != null){
                viewModel.showMyOrderDetails(tempOrder.getOrderId());
            }
        }
    }


}
