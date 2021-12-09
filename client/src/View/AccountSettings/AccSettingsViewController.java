package View.AccountSettings;

import Core.ViewHandler;
import Core.ViewModelFactory;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.util.Optional;

public class AccSettingsViewController
{

  public TextField firstNameTextField;
  public TextField lastNameTextField;
  public TextField emailTextField;
  public TextField userNameTextField;
  public TextField passwordTextField;
  public TextField rePasswordTextField;
  public Label errorRePasswordLabel;
  public Label fieldsError;
  public Label succesLabel;

  private ViewHandler viewHandler;
  private AccSettingsViewModel viewModel;

  public void init() {
    this.viewHandler = ViewHandler.getInstance();
    this.viewModel = ViewModelFactory.getAccSettingsViewModel();
    firstNameTextField.textProperty().bindBidirectional(viewModel.fNameProperty());
    lastNameTextField.textProperty().bindBidirectional(viewModel.lNameProperty());
    emailTextField.textProperty().bindBidirectional(viewModel.emailProperty());
    userNameTextField.textProperty().bindBidirectional(viewModel.userNameProperty());
    passwordTextField.textProperty().bindBidirectional(viewModel.passwordProperty());
    rePasswordTextField.textProperty().bindBidirectional(viewModel.rePasswordProperty());
    errorRePasswordLabel.textProperty().bindBidirectional(viewModel.errorPassProperty());
    fieldsError.textProperty().bindBidirectional(viewModel.errorFieldsProperty());
    succesLabel.textProperty().bindBidirectional(viewModel.successProperty());
  }


  public void editBtnPressed(ActionEvent actionEvent)
  {
    try
    {
      viewModel.sendEditedData();
    }catch (AccountEditedExceptionReply exReply){
      createAlert(Alert.AlertType.INFORMATION, exReply.getMessage()).showAndWait();
    }
  }

  public void deleteBtnPressed(ActionEvent actionEvent)
  {
    Alert alert = createAlert(Alert.AlertType.CONFIRMATION, "Do you really wish to delete your Account?");
    Optional<ButtonType> res = alert.showAndWait();
    if(res.isPresent())
    {
      if (res.get().equals(ButtonType.OK)) {
        try {
          viewModel.deleteCustomer();
        }catch (AccountDeletedExceptionReply exReply){
          //show pop-up with message about deletion AND logout
          createAlert(Alert.AlertType.INFORMATION, exReply.getMessage()).showAndWait();
          //TODO... logout missing !!!
          viewHandler.openBrowserPane();
        }
      }
    }
  }

  public void backBtnPressed(ActionEvent actionEvent)
  {
    viewHandler.openBrowserPane();
  }


  private Alert createAlert(Alert.AlertType alertType, String alertMsg){
    Alert alert = new Alert(alertType);
    alert.setTitle(alertType.toString());
    alert.setHeaderText(alertMsg);
    alert.setContentText("");

    return alert;
  }

}
