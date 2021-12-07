package View.Login;

import Core.ViewHandler;
import Core.ViewModelFactory;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.text.Text;

public class LoginViewController
{

    public TextField usernameTextField;
    public PasswordField passwordTextField;
    public Label usernameErrorLabel;
    public Label passwordErrorLabel;
    public Label successLabel;
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
        usernameErrorLabel.textProperty().bindBidirectional(viewModel.errorUserProperty());
        passwordErrorLabel.textProperty().bindBidirectional(viewModel.errorPassProperty());
        successLabel.textProperty().bindBidirectional(viewModel.successProperty());
         toggleGroup=new ToggleGroup();
        customerButton.setToggleGroup(toggleGroup);
        employeeButton.setToggleGroup(toggleGroup);
    }


    public void onRegister(ActionEvent actionEvent) {
        viewHandler.openRegisterPane();
    }


    public void onLogin(ActionEvent actionEvent) {
        RadioButton selected= (RadioButton) toggleGroup.getSelectedToggle();
        viewModel.login(selected.getText());

        if( successLabel.textProperty().getValue()!=null
            && successLabel.textProperty().getValue().contains("success") && selected.getText().equals("Customer"))
        {
            viewHandler.openBrowserPane();
        }
        else if( successLabel.textProperty().getValue()!=null
                && successLabel.textProperty().getValue().contains("MANAGER") ){
                viewHandler.openManagerPane();
        }
        else if(successLabel.textProperty().getValue()!=null && successLabel.textProperty().getValue().contains("ADMIN") ){
                viewHandler.openAdminPane();
        }
        else if(successLabel.textProperty().getValue() != null && successLabel.textProperty().getValue().contains("WAREHOUSE_WORKER")){
            viewHandler.openOrdersPane();
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
}
