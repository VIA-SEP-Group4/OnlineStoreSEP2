package View.Admin;

import Model.Employee;
import Model.ProductsModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdminViewModel {
    private ProductsModel productsModel;
    private ObservableList<Employee> managers;
    public AdminViewModel(ProductsModel productsModel) {
        this.productsModel=productsModel;
        managers= FXCollections.observableArrayList();
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
