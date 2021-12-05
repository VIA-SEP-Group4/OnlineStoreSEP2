package View.Admin;

import Enums.EmployeeType;
import Model.AdminModel;
import Model.Models.Employee;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

public class AdminViewModel {
    private AdminModel adminModel;
    private ObservableList<Employee> managers;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty pin;
    private StringProperty error;
    private BooleanProperty isAdded;
    public AdminViewModel(AdminModel adminModel) {
        this.adminModel=adminModel;
        firstName=new SimpleStringProperty();
        lastName=new SimpleStringProperty();
        pin=new SimpleStringProperty();
        error=new SimpleStringProperty();
        isAdded=new SimpleBooleanProperty();
        managers= FXCollections.observableArrayList();
        managers.addAll(adminModel.getManagerEmployees());
        isAdded=new SimpleBooleanProperty();
        adminModel.addListener("ManagerAddReply",this::replyAdd);
        adminModel.addListener("AdminReply",this::managersUpdate);
    }

    private void managersUpdate(PropertyChangeEvent event) {
        ArrayList<Employee> temp= (ArrayList<Employee>) event.getNewValue();
        managers.setAll(temp);
    }

    private void replyAdd(PropertyChangeEvent event) {
        String reply= (String) event.getNewValue();
        if(!reply.contains("approved")){
            error.setValue("An error occurred in the server! Try Again!");
        } else isAdded.setValue(true);
    }

    public ObservableList<Employee> getManagers() {
        return managers;
    }
    public void removeManager(Employee manager) {
        adminModel.removeManager(manager);
    }

    public void editManager() {
    }

    public void addNewManager() {
        isAdded.setValue(false);
        if(firstName.getValue()!=null && !firstName.getValue().equals("") &&
                lastName.getValue()!=null && !lastName.getValue().equals("") &&
                pin.getValue()!=null && !pin.getValue().equals("")
        ){
            if(pin.getValue().length()!=4) error.setValue("PIN can only be composed of 4 digits");
            else {
                adminModel.addManager(new Employee(firstName.getValue(),lastName.getValue(),Integer.parseInt(pin.getValue()), EmployeeType.MANAGER,0));
                clearFields();
            }

        }
        else {
            error.setValue("None of the fields can be left empty");
        }
    }
    public void clearFields(){
       firstName.setValue("");
       lastName.setValue("");
       pin.setValue("");
       error.setValue("");
    }
    public String getError() {
        return error.get();
    }
    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public String getPin() {
        return pin.get();
    }

    public StringProperty pinProperty() {
        return pin;
    }

    public StringProperty errorProperty() {
        return error;
    }

    public boolean isIsAdded() {
        return isAdded.get();
    }

    public BooleanProperty isAddedProperty() {
        return isAdded;
    }
}

