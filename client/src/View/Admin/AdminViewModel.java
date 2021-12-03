package View.Admin;

import Model.AdminModel;
import Model.Employee;
import Model.ProductsModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdminViewModel {
    private AdminModel adminModel;
    private ObservableList<Employee> managers;
    public AdminViewModel(AdminModel adminModel) {
        this.adminModel=adminModel;
        managers= FXCollections.observableArrayList();
        managers.addAll(adminModel.getManagerEmployees());
    }

    public ObservableList<Employee> getManagers() {
        return managers;
    }

    public void addNewManager() {
    }

    public void removeManager() {
    }

    public void editManager() {
    }
}
