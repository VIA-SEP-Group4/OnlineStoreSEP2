package View.Login;

import Core.ViewHandler;
import Core.ViewModelFactory;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

public class LoginViewController
{

  public TextField usernameTextField;
  public PasswordField passwordTextField;
  public Label successLabel;
  public Label errorLabel;
  public RadioButton customerButton;
  public RadioButton employeeButton;
  public Text usernameLabel;
  public Text passwordLabel;

  private ViewHandler viewHandler;
  private LoginViewModel viewModel;
  private ToggleGroup toggleGroup;

  public void init() {
    this.viewHandler = ViewHandler.getInstance();
    this.viewModel = ViewModelFactory.getLoginViewModel();
    usernameTextField.textProperty().bindBidirectional(viewModel.userNameProperty());
    passwordTextField.textProperty().bindBidirectional(viewModel.passwordProperty());
    successLabel.textProperty().bindBidirectional(viewModel.successProperty());
    errorLabel.textProperty().bindBidirectional(viewModel.errorProperty());
    toggleGroup=new ToggleGroup();
    customerButton.setToggleGroup(toggleGroup);
    employeeButton.setToggleGroup(toggleGroup);

    clearErrorLabels();
  }

  public void onRegister(ActionEvent actionEvent) {
        viewHandler.openRegisterPane();
    }

    public void onLogin(ActionEvent actionEvent) {
        logIn();
    }

    public void onEnter(KeyEvent keyEvent)
    {
      if (keyEvent.getCode() == KeyCode.ENTER) {
        logIn();
      }
    }

  public void backBtnPressed(ActionEvent actionEvent)
  {
      viewHandler.openBrowserPane();
  }

  public void customerSelected(ActionEvent actionEvent)
  {
      usernameLabel.setText("Username");
      passwordLabel.setText("Password");
  }

  public void employeeSelected(ActionEvent actionEvent)
  {
      usernameLabel.setText("Employee ID");
      passwordLabel.setText("PIN");
  }

  private void logIn(){

    RadioButton selected = (RadioButton) toggleGroup.getSelectedToggle();
    viewModel.login(selected.getText());

    if (successLabel.textProperty().getValue() != null
        && successLabel.textProperty().getValue().contains("success")
        && selected.getText().equals("Customer"))
    {
      viewHandler.openBrowserPane();
    }
    else if (successLabel.textProperty().getValue() != null
        && successLabel.textProperty().getValue().contains("MANAGER"))
    {
      viewHandler.openManagerPane();
    }
    else if (successLabel.textProperty().getValue() != null
        && successLabel.textProperty().getValue().contains("ADMIN"))
    {
      viewHandler.openAdminPane();
    }
    else if (successLabel.textProperty().getValue() != null
        && successLabel.textProperty().getValue().contains("WAREHOUSE_WORKER"))
    {
      viewHandler.openOrdersPane();
    }
  }

  private void clearErrorLabels()
  {
    errorLabel.setText("");
    successLabel.setText("");
  }
}