package View.Login;

import Core.ViewHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginViewController  {

    public TextField usernameTextField;
    public PasswordField passwordTextField;
    public Label usernameErrorLabel;
    public Label passwordErrorLabel;
    private ViewHandler viewHandler;
    private LoginViewModel viewModel;
    public void init(ViewHandler viewHandler, LoginViewModel viewModel) {
        this.viewHandler=viewHandler;
        this.viewModel=viewModel;
    }

    public void onRegister(ActionEvent actionEvent) {
        viewHandler.openRegisterPane();
    }
}
