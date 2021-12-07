package View.Manager;

import Core.ViewHandler;
import Core.ViewModelFactory;
import Model.Models.Employee;
import Model.Models.Order;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.sql.Time;

public class WorkersOverviewController {
    public TableColumn<Employee,Integer> IDCol;
    public TableColumn<Employee,String> firstNameCol;
    public TableColumn<Employee, String> lastNameCol;
    public TableColumn<Employee,Integer> pinCol;
    public TableView<Employee> workersTable;
    public TableView<Order> responsibleTable;
    public TableColumn<Order, Integer> respOrderIdCol;
    public TableColumn<Order, String> respStatusCol;
    public TableColumn<Order, String> respTimeCol;
    private WorkersOverviewViewModel viewModel;
    private ViewHandler viewHandler;
    public void init() {
        viewHandler=ViewHandler.getInstance();
        this.viewModel= ViewModelFactory.getWorkersOverviewViewModel();
        workersTable.setItems(viewModel.getWorkers());
        IDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        pinCol.setCellValueFactory(new PropertyValueFactory<>("pin"));
        responsibleTable.setItems(viewModel.getWorkerOrders());
        respOrderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        respStatusCol.setCellValueFactory(new PropertyValueFactory<>("state"));
        respTimeCol.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
    }

    public void onAddEmployee(ActionEvent actionEvent) {
        viewHandler.openWorkersDetailPane();
    }

    public void onRemove(ActionEvent actionEvent) {
        Employee e=workersTable.getSelectionModel().getSelectedItem();
        viewModel.removeWorker(e);
    }

    public void onEdit(ActionEvent actionEvent) {
        Employee e=workersTable.getSelectionModel().getSelectedItem();
        viewModel.setEditFields(e);
        viewHandler.openWorkersDetailPane();
    }

    public void onSelectWorker(MouseEvent mouseEvent) {
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY) &&  mouseEvent.getClickCount()==2){
            Employee emp=workersTable.getSelectionModel().getSelectedItem();
            if(emp!=null )
                viewModel.setWorkerOrders(emp.getID());
        }
    }
    //TODO Back to Login button
}
