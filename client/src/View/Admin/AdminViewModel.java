package View.Admin;

import Enums.EmployeeType;
import Model.CredentialsModel;
import Model.Models.Employee;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

public class AdminViewModel {

    private CredentialsModel credentialsModel;
    private ObservableList<Employee> managers;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty pin;
    private StringProperty error;
    private BooleanProperty isAdded;
    private boolean isEdit=false;
    private int tempId=0;

    public AdminViewModel(CredentialsModel model) {
        this.credentialsModel = model;
        firstName=new SimpleStringProperty();
        lastName=new SimpleStringProperty();
        pin=new SimpleStringProperty();
        error=new SimpleStringProperty();
        isAdded=new SimpleBooleanProperty();
        managers= FXCollections.observableArrayList();
        managers.addAll(model.getEmployees("Manager"));
        credentialsModel.addListener("ManagerAddReply",this::replyAdd);
        credentialsModel.addListener("AdminReply",this::managersUpdate);
        credentialsModel.addListener("AdminReplyDelete",this::managersDelete);
    }

    private void managersDelete(PropertyChangeEvent event) {
        Employee e=(Employee) event.getNewValue();
        for(Employee emp: managers){
            if(emp.getID()==e.getID()){
                managers.remove(emp);
                break;
            }
        }
    }

    private void managersUpdate(PropertyChangeEvent event) {
        Employee manager= (Employee) event.getNewValue();
        boolean found=false;
        for(int i=0;i<managers.size();i++){
            if(managers.get(i).getID()== manager.getID()){
                managers.set(i,manager);
                found=true;
                break;
            }
        }
        if(!found){
            managers.add(manager);
        }
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
        credentialsModel.removeEmployee(manager);
    }

    public void editManager(Employee e) {
        firstName.setValue(e.getFirstName());
        lastName.setValue(e.getLastName());
        pin.setValue(String.valueOf(e.getPin()));
        isEdit=true;
        tempId=e.getID();
    }

    public void addNewManager() {
        isAdded.setValue(false);
        if(firstName.getValue()!=null && !firstName.getValue().equals("") &&
                lastName.getValue()!=null && !lastName.getValue().equals("") &&
                pin.getValue()!=null && !pin.getValue().equals("")
        ){
            if(pin.getValue().length()!=4) error.setValue("PIN can only be composed of 4 digits");
            else if(!isEdit) {
                credentialsModel.addEmployee(new Employee(firstName.getValue(),lastName.getValue(),Integer.parseInt(pin.getValue()), EmployeeType.MANAGER,0));
                clearFields();
            }else{
                credentialsModel.editEmployee(new Employee(firstName.getValue(),lastName.getValue(),Integer.parseInt(pin.getValue()),EmployeeType.MANAGER,tempId));
                isEdit=false;
                isAdded.setValue(true);
                clearFields();
            }

        }
        else {
            error.setValue("None of the fields can be left empty");
        }
    }

    public void logOut()
    {
        credentialsModel.logOutEmployee();
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

