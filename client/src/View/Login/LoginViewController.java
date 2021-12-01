package View.Login;

import Core.ViewHandler;
import Core.ViewModelFactory;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginViewController
{

    public TextField usernameTextField;
    public PasswordField passwordTextField;
    public Label usernameErrorLabel;
    public Label passwordErrorLabel;
    public Label successLabel;

    private ViewHandler viewHandler;
    private LoginViewModel viewModel;

    public void init() {
        this.viewHandler = ViewHandler.getInstance();
        this.viewModel = ViewModelFactory.getLoginViewModel();
        usernameTextField.textProperty().bindBidirectional(viewModel.userNameProperty());
        passwordTextField.textProperty().bindBidirectional(viewModel.passwordProperty());
        usernameErrorLabel.textProperty().bindBidirectional(viewModel.errorUserProperty());
        passwordErrorLabel.textProperty().bindBidirectional(viewModel.errorPassProperty());
        successLabel.textProperty().bindBidirectional(viewModel.successProperty());
    }


    public void onRegister(ActionEvent actionEvent) {
        viewHandler.openRegisterPane();
    }


    public void onLogin(ActionEvent actionEvent) {
        viewModel.login();

        if( successLabel.textProperty().getValue()!=null
            && successLabel.textProperty().getValue().contains("success"))
        {
            viewHandler.openBrowserPane();
//            viewHandler.openProductsPane();
        }

    }


}
