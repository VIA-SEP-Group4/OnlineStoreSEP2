package View.Register;

import Model.LoginModel;
import Model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RegisterViewModel {
    private LoginModel model;
    private StringProperty fName;
    private StringProperty lName;
    private StringProperty userName;
    private StringProperty email;
    private StringProperty password;
    private StringProperty rePassword;

    public RegisterViewModel(LoginModel model) {
        this.model=model;
        fName=new SimpleStringProperty();
        lName=new SimpleStringProperty();
        userName=new SimpleStringProperty();
        email=new SimpleStringProperty();
        password=new SimpleStringProperty();
        rePassword=new SimpleStringProperty();
    }

    public String getfName() {
        return fName.get();
    }
    public void sendRegisterData(){
        if(!fName.getValue().equals("") && fName!=null
        && !lName.getValue().equals("") && lName!=null
        && !userName.getValue().equals("") && userName!=null
        && !email.getValue().equals("") && email!=null
        && !password.getValue().equals("") && password!=null
        )
            model.registerUser(new User(fName.getValue(),lName.getValue(),userName.getValue(),email.getValue(),password.getValue()));
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
}
