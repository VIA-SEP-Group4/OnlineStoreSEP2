package View.AccountSettings;

import Model.CredentialsModel;
import Model.Models.Customer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AccSettingsViewModel
{

  private CredentialsModel credentialsModel;
  private StringProperty fName;
  private StringProperty lName;
  private StringProperty userName;
  private StringProperty email;
  private StringProperty password;
  private StringProperty rePassword;
  private StringProperty errorPass;
  private StringProperty errorFields;
  private StringProperty success;

  public AccSettingsViewModel(CredentialsModel model) {
    this.credentialsModel = model;
    fName = new SimpleStringProperty();
    lName = new SimpleStringProperty();
    userName = new SimpleStringProperty();
    email = new SimpleStringProperty();
    password = new SimpleStringProperty();
    rePassword = new SimpleStringProperty();
    errorPass = new SimpleStringProperty();
    errorFields = new SimpleStringProperty();
    success = new SimpleStringProperty();
  }


  public void sendEditedData(){
    clearLabels();

    if(fName.getValue()!=null && !fName.getValue().equals("")
        && lName.getValue()!=null && !lName.getValue().equals("")
        && userName.getValue()!=null && !userName.getValue().equals("")
        && email.getValue()!=null && !email.getValue().equals("")
        && password.getValue()!=null && !password.getValue().equals("")
        && rePassword.getValue()!=null && !rePassword.getValue().equals("") )
    {
      if(!password.getValue().equals(rePassword.getValue()))
      {
        errorPass.setValue("Passwords don't match");
      }
      else {
        Customer editedCustomer = new Customer(userName.getValue(), password.getValue(), email.getValue(), fName.getValue(), lName.getValue());
        clearFields();
        credentialsModel.editCustomer(editedCustomer);
      }
    }
    else{
      errorFields.setValue("Fields cannot be empty on editing"); errorPass.setValue("");
    }
  }


  private void clearFields()
  {
    fName.set(null);
    lName.set(null);
    userName.set(null);
    email.set(null);
    password.set(null);
    rePassword.set(null);
  }

  private void clearLabels()
  {
    errorFields.setValue("");
    errorPass.setValue("");
  }

  public String getfName() {
    return fName.get();
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

  public void deleteCustomer()
  {
    credentialsModel.deleteCustomer();
  }
}
