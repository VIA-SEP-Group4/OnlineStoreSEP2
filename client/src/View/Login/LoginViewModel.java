package View.Login;

import Model.CredentialsModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginViewModel implements PropertyChangeListener {

    private CredentialsModel credentialsModel;
    private StringProperty userName;
    private StringProperty password;
    private StringProperty errorUser;
    private StringProperty errorPass;
    private StringProperty success;

    public LoginViewModel(CredentialsModel credentialsModel) {
        this.credentialsModel = credentialsModel;
        userName=new SimpleStringProperty();
        password=new SimpleStringProperty();
        errorPass=new SimpleStringProperty();
        errorUser=new SimpleStringProperty();
        success=new SimpleStringProperty();
        credentialsModel.addListener("LoginReply",this);
    }

    /**
     * Method called when user tries to log in with inserted credentials.
     */
    public void login(String userType) {
        clearLabels();
        if(userName.getValue()==null || userName.getValue().equals("")) {
            errorUser.setValue("Username cannot be empty");
            success.setValue("denied");
        }

        else if(password.getValue()==null || password.getValue().equals("")){
            errorPass.setValue("Password cannot be empty");
            success.setValue("denied");
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

    public String getErrorUser() {
        return errorUser.get();
    }

    public StringProperty errorUserProperty() {
        return errorUser;
    }

    public String getErrorPass() {
        return errorPass.get();
    }

    public StringProperty errorPassProperty() {
        return errorPass;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String reply=evt.getNewValue().toString();
        //success.setValue("denied");
        success.setValue(reply);
        if(reply.contains("Password")){ Platform.runLater(()-> {
            errorPass.setValue(reply);
        errorUser.setValue("");
        });
        }
        else if(reply.contains("User")){ Platform.runLater(()->{
            errorUser.setValue(reply);
            errorPass.setValue("");
        });}
        else if (reply.contains("database")){
            Platform.runLater(()->{success.setValue(reply);});
        }
        else if (reply.toLowerCase().contains("success"))
        {
//            Platform.runLater(()->{
//                success.setValue(reply);
//                errorPass.setValue("");
//                errorUser.setValue("");
//            });

            success.setValue(reply);
            errorPass.setValue("");
            errorUser.setValue("");
        }


        clearFields();
    }

    /**
     * Method clearing all view fields.
     */
    private void clearFields()
    {
        userName.set(null);
        password.set(null);
    }

    private void clearLabels()
    {
        success.setValue("");
        errorUser.setValue("");
        errorPass.setValue("");
    }

    public String getSuccess() {
        return success.get();
    }

    public StringProperty successProperty() {
        return success;
    }
}
