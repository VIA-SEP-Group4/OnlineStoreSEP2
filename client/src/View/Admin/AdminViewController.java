package View.Admin;

import Core.ViewHandler;
import Core.ViewModelFactory;
import Model.Employee;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminViewController {
    public TableView<Employee> managersTable;
    public TableColumn<Employee, Integer> IDCol;
    public TableColumn<Employee, String> firstNameCol;
    public TableColumn<Employee, String> lastNameCol;
    public TableColumn<Employee, Integer> pinCol;
    public AdminViewModel viewModel;
    public ViewHandler viewHandler;
    public void init() {
        viewModel= ViewModelFactory.getAdminViewModel();
        viewHandler=ViewHandler.getInstance();
        managersTable.setItems(viewModel.getManagers());
        IDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        pinCol.setCellValueFactory(new PropertyValueFactory<>("pin"));
    }
    public void addNewManager(ActionEvent actionEvent) {
        viewModel.addNewManager();
    }

    public void removeManager(ActionEvent actionEvent) {
        viewModel.removeManager();
    }

    public void editManager(ActionEvent actionEvent) {
        viewModel.editManager();
    }


    public void backToLogin(ActionEvent actionEvent) {
        viewHandler.openLoginPane();
    }
}
