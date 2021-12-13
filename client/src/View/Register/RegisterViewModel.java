package View.Register;

import Model.CredentialsModel;
import Model.Models.Customer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class RegisterViewModel implements PropertyChangeListener {
    private CredentialsModel model;
    private StringProperty fName;
    private StringProperty lName;
    private StringProperty userName;
    private StringProperty email;
    private StringProperty password;
    private StringProperty rePassword;
    private StringProperty success;

    public RegisterViewModel(CredentialsModel model) {
        this.model=model;
        fName=new SimpleStringProperty();
        lName=new SimpleStringProperty();
        userName=new SimpleStringProperty();
        email=new SimpleStringProperty();
        password=new SimpleStringProperty();
        rePassword=new SimpleStringProperty();
        success=new SimpleStringProperty();
        model.addListener("RegistrationReply",this);
    }

    public StringProperty fNameProperty() {
        return fName;
    }

    public StringProperty lNameProperty() {
        return lName;
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public StringProperty rePasswordProperty() {
        return rePassword;
    }

    public StringProperty successProperty()
    {
        return success;
    }

    /**
     * Method called when registering new user.
     * Checking if all required fields were filled and password and re-entered password match
     */
    public void sendRegisterData()
    {
        if(fName.getValue()!=null && !fName.getValue().equals("")
        && lName.getValue()!=null && !lName.getValue().equals("")
        && userName.getValue()!=null && !userName.getValue().equals("")
        && email.getValue()!=null && !email.getValue().equals("")
        && password.getValue()!=null && !password.getValue().equals("")
        && rePassword.getValue()!=null && !rePassword.getValue().equals("") )
        {
            if(!password.getValue().equals(rePassword.getValue()))
            {
                createAlert(Alert.AlertType.ERROR,"Passwords don't match").showAndWait();
            }
            else {
                model.registerUser(new Customer(userName.getValue(), password.getValue(), email.getValue(), fName.getValue(), lName.getValue()));

            }
        }
        else{
            createAlert(Alert.AlertType.ERROR,"Fields cannot be empty on registering").showAndWait();
        }
    }

    private Alert createAlert(Alert.AlertType alertType, String alertMsg){
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType.toString());
        alert.setHeaderText(alertMsg);
        alert.setContentText("");

        return alert;
    }
    /**
     * Method clearing all view fields.
     */
    private void clearFields()
    {
        fName.set(null);
        lName.set(null);
        userName.set(null);
        email.set(null);
        password.set(null);
        rePassword.set(null);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String reply=evt.getNewValue().toString();
        if(reply.contains("approved")){
            createAlert(Alert.AlertType.INFORMATION,"Registration was successful!").showAndWait();
            success.setValue("success");
            //->change view to LogIn-view automatically?
        }
        else
        {
            createAlert(Alert.AlertType.INFORMATION,"Registration failed").showAndWait();
        }
        clearFields();
    }
}
