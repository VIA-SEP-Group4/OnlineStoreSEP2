package View.Login;

import Model.CredentialsModel;
import Model.ProductsModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginViewModel implements PropertyChangeListener {

    private CredentialsModel credentialsModel;
    private ProductsModel productsModel;
    private StringProperty userName;
    private StringProperty password;
    private StringProperty success;
    private StringProperty error;

    public LoginViewModel(CredentialsModel credentialsModel, ProductsModel productsModel) {
        this.credentialsModel = credentialsModel;
        this.productsModel = productsModel;
        userName=new SimpleStringProperty();
        password=new SimpleStringProperty();
        success=new SimpleStringProperty();
        error=new SimpleStringProperty();

        credentialsModel.addListener("LoginReply",this);
    }

    /**
     * Method called when user tries to log in with inserted credentials.
     */
    public void login(String userType) {

        if(userName.getValue()==null || userName.getValue().equals("")) {
            error.setValue("Username cannot be empty");
        }

        else if(password.getValue()==null || password.getValue().equals("")){
            error.setValue("Password cannot be empty");
        }

        else{
            credentialsModel.login(userName.getValue(),password.getValue(),userType);
        }
    }

    public String getUserName() {
        return userName.get();
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public StringProperty successProperty()
    {
        return success;
    }

    public StringProperty errorProperty() {
        return error;
    }

    private Alert createAlert(Alert.AlertType alertType, String alertMsg){
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType.toString());
        alert.setHeaderText(alertMsg);
        alert.setContentText("");

        return alert;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String reply=evt.getNewValue().toString();
        if (reply.toLowerCase().contains("success"))
        {
            success.setValue(reply);
        }
        else
        {
            error.setValue("");
            createAlert(Alert.AlertType.ERROR,reply).showAndWait();
        }
    }
}