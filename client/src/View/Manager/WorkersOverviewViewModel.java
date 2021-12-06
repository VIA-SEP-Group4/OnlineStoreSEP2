package View.Manager;

import Enums.EmployeeType;
import Model.ManagerModel;
import Model.Models.Employee;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

public class WorkersOverviewViewModel {
    private ManagerModel model;
    private ObservableList<Employee> employees;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty pin;
    private StringProperty error;
    private BooleanProperty isAdded;
    public WorkersOverviewViewModel(ManagerModel managerModel) {
        model=managerModel;
        employees= FXCollections.observableArrayList();
        employees.addAll(model.getWorkers());
        firstName=new SimpleStringProperty();
        lastName=new SimpleStringProperty();
        pin=new SimpleStringProperty();
        error=new SimpleStringProperty();
        isAdded=new SimpleBooleanProperty();
        model.addListener("ManagerWorkersReply",this::updateWorkers);
        model.addListener("AddedWorker",this::replyAdd);
    }

    private void replyAdd(PropertyChangeEvent event) {
        String reply= (String) event.getNewValue();
        if(!reply.contains("approved")){
            error.setValue("An error occurred in the server! Try Again!");
        } else isAdded.setValue(true);
    }

    private void updateWorkers(PropertyChangeEvent event) {
        ArrayList<Employee> temp= (ArrayList<Employee>) event.getNewValue();
        employees.setAll(temp);
    }

    public ObservableList<Employee> getWorkers() {
        return  employees;
    }

    public void addNewWorker() {
        isAdded.setValue(false);
        if(firstName.getValue()!=null && !firstName.getValue().equals("") &&
                lastName.getValue()!=null && !lastName.getValue().equals("") &&
                pin.getValue()!=null && !pin.getValue().equals("")
        ){
            if(pin.getValue().length()!=4) error.setValue("PIN can only be composed of 4 digits");
            else {
                model.addWorker(new Employee(firstName.getValue(),lastName.getValue(),Integer.parseInt(pin.getValue()), EmployeeType.WAREHOUSE_WORKER,0));
                clearFields();
            }

        }
        else {
            error.setValue("None of the fields can be left empty");
        }
    }
    public void removeWorker(Employee e) {
        model.removeWorker(e);
    }

    public String getFirstName() {
        return firstName.get();
    }
    public void clearFields(){
        firstName.setValue("");
        lastName.setValue("");
        pin.setValue("");
        error.setValue("");
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

    public String getError() {
        return error.get();
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
