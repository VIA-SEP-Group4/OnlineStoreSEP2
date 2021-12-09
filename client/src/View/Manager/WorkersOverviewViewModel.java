package View.Manager;

import Enums.EmployeeType;
import Model.CredentialsModel;
import Model.Models.Employee;
import Model.Models.Order;
import Model.OrdersModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

public class WorkersOverviewViewModel {
    private CredentialsModel model;
    private OrdersModel ordersModel;
    private ObservableList<Employee> employees;
    private ObservableList<Order> responsibleFor;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty pin;
    private StringProperty error;
    private BooleanProperty isAdded;
    private boolean isEdit=false;
    private int tempID=0;
    public WorkersOverviewViewModel(CredentialsModel model,OrdersModel ordersModel) {
        this.ordersModel=ordersModel;
        this.model=model;
        employees= FXCollections.observableArrayList();
        responsibleFor= FXCollections.observableArrayList();
        employees.addAll(model.getEmployees("Worker"));
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
            else if(!isEdit){
                model.addEmployee(new Employee(firstName.getValue(),lastName.getValue(),Integer.parseInt(pin.getValue()), EmployeeType.WAREHOUSE_WORKER,0));
                clearFields();
            }else {
                model.editEmployee(new Employee(firstName.get(),lastName.getValue(),Integer.parseInt(pin.getValue()),EmployeeType.WAREHOUSE_WORKER,tempID));
                isEdit=false;
                tempID=0;
                isAdded.setValue(true);
                clearFields();
            }

        }
        else {
            error.setValue("None of the fields can be left empty");
        }
    }
    public void removeWorker(Employee e) {
        model.removeEmployee(e);
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


    public void setWorkerOrders(int wwId) {
        responsibleFor.setAll(ordersModel.getWorkerOrdersForManager(wwId));
    }
    public ObservableList<Order> getWorkerOrders(){
        return responsibleFor;
    }

    public void setEditFields(Employee e) {
        firstName.setValue(e.getFirstName());
        lastName.setValue(e.getLastName());
        pin.setValue(String.valueOf(e.getPin()));
        isEdit=true;
        tempID=e.getID();
    }
}
