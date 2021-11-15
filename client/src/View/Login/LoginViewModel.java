package View.Login;

import Model.LoginModel;
import Model.User;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginViewModel implements PropertyChangeListener {
    private LoginModel loginModel;
    private StringProperty userName;
    private StringProperty password;
    private StringProperty errorUser;
    private StringProperty errorPass;
    private StringProperty success;
    public LoginViewModel(LoginModel loginModel) {
        this.loginModel = loginModel;
        userName=new SimpleStringProperty();
        password=new SimpleStringProperty();
        errorPass=new SimpleStringProperty();
        errorUser=new SimpleStringProperty();
        success=new SimpleStringProperty();
        loginModel.addListener("LoginReply",this);
    }
    public void login() {
        if(userName.getValue().equals("") || userName==null  ) errorUser.setValue("Username cannot be empty");
        else if(password.getValue().equals("") || password==null) errorPass.setValue("Password cannot be empty");
        else loginModel.login(userName.getValue(),password.getValue());
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
        if(reply.contains("Password")){ Platform.runLater(()-> {
            errorPass.setValue(reply);
        errorUser.setValue("");
        });
        }
        else if(reply.contains("User")){ Platform.runLater(()->{
            errorUser.setValue(reply);
            errorPass.setValue("");
        });}
        else Platform.runLater(()->{success.setValue(reply);
                errorPass.setValue("");
                errorUser.setValue("");
        });
    }

    public String getSuccess() {
        return success.get();
    }

    public StringProperty successProperty() {
        return success;
    }
}