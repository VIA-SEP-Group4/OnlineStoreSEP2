package View.Login;

import Core.ViewHandler;
import Core.ViewModelFactory;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

public class LoginViewController
{

    public TextField usernameTextField;
    public PasswordField passwordTextField;
    public Label usernameErrorLabel;
    public Label passwordErrorLabel;
    public Label successLabel;
    public RadioButton customerButton;
    public RadioButton employeeButton;

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
            && successLabel.textProperty().getValue().contains("success"))
        {
            viewHandler.openBrowserPane();
//            viewHandler.openProductsPane();
        }

    }

  public void backBtnPressed(ActionEvent actionEvent)
  {
      viewHandler.openBrowserPane();
  }
}
