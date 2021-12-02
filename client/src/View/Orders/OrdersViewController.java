package View.Orders;

import Core.ViewHandler;
import Core.ViewModelFactory;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class OrdersViewController
{
    private ViewHandler viewHandler;
    private OrdersViewModel viewModel;

    public TableView openOrdersTable;
    public TableColumn IDColumn;
    public TableColumn timeColumn;
    public TableColumn totalPriceColumn;
    public TableColumn statusColumn;

    public TableView openDetailTable;
    public TableColumn nameColumn;
    public TableColumn typeColumn;
    public TableColumn unitPriceColumn;
    public TableColumn quantityColumn;
    public TableColumn descriptionColumn;

    public TableView myOrdersTable;
    public TableColumn myIDColumn;
    public TableColumn myTimeColumn;
    public TableColumn myTotalPriceColumn;
    public TableColumn myStatusColumn;

    public TableView myDetailTable;
    public TableColumn myNameColumn;
    public TableColumn myTypeColumn;
    public TableColumn myUnitPriceColumn;
    public TableColumn myQuantityColumn;
    public TableColumn myDescriptionColumn;

    public void init(ViewHandler viewHandler){
        this.viewHandler = viewHandler;
        this.viewModel = ViewModelFactory.getOrdersViewModel();
    }


    public void pickOrderButton(ActionEvent actionEvent)
    {
    }

    public void removeButton(ActionEvent actionEvent)
    {
    }

    public void changeStateButton(ActionEvent actionEvent)
    {
    }
}
