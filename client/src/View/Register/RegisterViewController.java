package View.Register;

import Core.ViewHandler;
import Core.ViewModelFactory;
import View.Login.LoginViewModel;
import View.ViewController;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class RegisterViewController implements ViewController
{
    public TextField firstNameTextField;
    public TextField lastNameTextField;
    public TextField emailTextField;
    public TextField userNameTextField;
    public TextField passwordTextField;
    public TextField rePasswordTextField;

    private ViewHandler viewHandler;
    private RegisterViewModel viewModel;

    public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
        this.viewHandler=viewHandler;
        this.viewModel = viewModelFactory.getRegisterViewModel();
        firstNameTextField.textProperty().bindBidirectional(viewModel.fNameProperty());
        lastNameTextField.textProperty().bindBidirectional(viewModel.lNameProperty());
        emailTextField.textProperty().bindBidirectional(viewModel.emailProperty());
        userNameTextField.textProperty().bindBidirectional(viewModel.userNameProperty());
        passwordTextField.textProperty().bindBidirectional(viewModel.passwordProperty());
    }

    public void onSignUp(ActionEvent actionEvent) {
        viewModel.sendRegisterData();
    }
}
