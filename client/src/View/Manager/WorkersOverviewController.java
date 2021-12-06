package View.Manager;

import Core.ViewHandler;
import Core.ViewModelFactory;
import Model.Models.Employee;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class WorkersOverviewController {
    public TableColumn<Employee,Integer> IDCol;
    public TableColumn<Employee,String> firstNameCol;
    public TableColumn<Employee, String> lastNameCol;
    public TableColumn<Employee,Integer> pinCol;
    public TableView<Employee> workersTable;
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
    }

    public void onAddEmployee(ActionEvent actionEvent) {
        viewHandler.openWorkersDetailPane();
    }

    public void onRemove(ActionEvent actionEvent) {
        Employee e=workersTable.getSelectionModel().getSelectedItem();
        viewModel.removeWorker(e);
    }

    public void onEdit(ActionEvent actionEvent) {
    }
    //TODO Back to Login button
}
