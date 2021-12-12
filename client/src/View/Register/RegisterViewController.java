package View.Register;

import Core.ViewHandler;
import Core.ViewModelFactory;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegisterViewController
{
    public TextField firstNameTextField;
    public TextField lastNameTextField;
    public TextField emailTextField;
    public TextField userNameTextField;
    public TextField passwordTextField;
    public TextField rePasswordTextField;
    public Label successLabel;

    private ViewHandler viewHandler;
    private RegisterViewModel viewModel;

    public void init() {
        this.viewHandler=ViewHandler.getInstance();
        this.viewModel = ViewModelFactory.getRegisterViewModel();
        firstNameTextField.textProperty().bindBidirectional(viewModel.fNameProperty());
        lastNameTextField.textProperty().bindBidirectional(viewModel.lNameProperty());
        emailTextField.textProperty().bindBidirectional(viewModel.emailProperty());
        userNameTextField.textProperty().bindBidirectional(viewModel.userNameProperty());
        passwordTextField.textProperty().bindBidirectional(viewModel.passwordProperty());
        rePasswordTextField.textProperty().bindBidirectional(viewModel.rePasswordProperty());
        successLabel.textProperty().bindBidirectional(viewModel.successProperty());
    }

    public void onSignUp(ActionEvent actionEvent) {
        viewModel.sendRegisterData();
        if (successLabel.textProperty().getValue()!= null && successLabel.textProperty().getValue().contains("success"))
        {
            viewHandler.openLoginPane();
        }
    }

    public void backToLogin(ActionEvent actionEvent) {
        viewHandler.openLoginPane();
    }
}
