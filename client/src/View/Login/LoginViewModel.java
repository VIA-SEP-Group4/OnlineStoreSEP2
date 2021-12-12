package View.Login;

import Model.CredentialsModel;
import Model.ProductsModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginViewModel implements PropertyChangeListener {

    private CredentialsModel credentialsModel;
    private ProductsModel productsModel;
    private StringProperty userName;
    private StringProperty password;
    private StringProperty success;

    public LoginViewModel(CredentialsModel credentialsModel, ProductsModel productsModel) {
        this.credentialsModel = credentialsModel;
        this.productsModel = productsModel;
        userName=new SimpleStringProperty();
        password=new SimpleStringProperty();
        success=new SimpleStringProperty();

        credentialsModel.addListener("LoginReply",this);
    }

    /**
     * Method called when user tries to log in with inserted credentials.
     */
    public void login(String userType) {

        if(userName.getValue()==null || userName.getValue().equals("")) {
            prompt("Username cannot be empty","Wrong Input");
        }

        else if(password.getValue()==null || password.getValue().equals("")){
            prompt("Password cannot be empty","Wrong Input");
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

    public void prompt(String message, String title)
    {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String reply=evt.getNewValue().toString();
        if (reply.toLowerCase().contains("success"))
        {
            success.setValue(reply);
        }
        prompt(reply,"Access Denied");
    }
}