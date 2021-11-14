package View.Login;

import Core.ViewHandler;
import Core.ViewModelFactory;
import View.ViewController;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginViewController implements ViewController
{

    public TextField usernameTextField;
    public PasswordField passwordTextField;
    public Label usernameErrorLabel;
    public Label passwordErrorLabel;

    private ViewHandler viewHandler;
    private LoginViewModel viewModel;

    public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModelFactory.getLoginViewModel();
    }

    public void onRegister(ActionEvent actionEvent) {
        viewHandler.openRegisterPane();
    }
}
