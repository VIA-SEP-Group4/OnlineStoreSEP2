package View.Register;

import Model.LoginModel;
import Model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class RegisterViewModel implements PropertyChangeListener {
    private LoginModel model;
    private StringProperty fName;
    private StringProperty lName;
    private StringProperty userName;
    private StringProperty email;
    private StringProperty password;
    private StringProperty rePassword;
    private StringProperty errorPass;
    private StringProperty errorFields;
    private StringProperty success;
    public RegisterViewModel(LoginModel model) {
        this.model=model;
        fName=new SimpleStringProperty();
        lName=new SimpleStringProperty();
        userName=new SimpleStringProperty();
        email=new SimpleStringProperty();
        password=new SimpleStringProperty();
        rePassword=new SimpleStringProperty();
        errorPass=new SimpleStringProperty();
        errorFields=new SimpleStringProperty();
        success=new SimpleStringProperty();
        model.addListener("RegistrationReply",this);
    }

    public String getfName() {
        return fName.get();
    }
    public void sendRegisterData(){
        if(!fName.getValue().equals("") && fName!=null
        && !lName.getValue().equals("") && lName!=null
        && !userName.getValue().equals("") && userName!=null
        && !email.getValue().equals("") && email!=null
        && password!=null && !password.getValue().equals("")
                && !rePassword.getValue().equals("") && rePassword!=null)
        {
            if(!password.getValue().equals(rePassword.getValue()))
            {
                errorPass.setValue("Passwords don't match");
            }
            else { model.registerUser(new User(userName.getValue(), password.getValue(), email.getValue(), fName.getValue(), lName.getValue()));
                 errorPass.setValue("");
                 errorFields.setValue("");
            }
        }
        else{ errorFields.setValue("Fields cannot be empty on registering "); errorPass.setValue(""); }
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String reply=evt.getNewValue().toString();
        if(reply.contains("approved")){
            success.setValue("Registration was successful!");
            errorFields.setValue("");
        }
        else
        {
            errorFields.setValue("Registration failed");
            success.setValue("");
        }
    }

    public StringProperty fNameProperty() {
        return fName;
    }

    public String getlName() {
        return lName.get();
    }

    public StringProperty lNameProperty() {
        return lName;
    }

    public String getUserName() {
        return userName.get();
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public String getRePassword() {
        return rePassword.get();
    }

    public StringProperty rePasswordProperty() {
        return rePassword;
    }

    public String getErrorPass() {
        return errorPass.get();
    }

    public StringProperty errorPassProperty() {
        return errorPass;
    }

    public String getErrorFields() {
        return errorFields.get();
    }

    public StringProperty errorFieldsProperty() {
        return errorFields;
    }



    public String getSuccess() {
        return success.get();
    }

    public StringProperty successProperty() {
        return success;
    }
}
