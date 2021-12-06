package View.Manager;

import Core.ViewHandler;
import Core.ViewModelFactory;
import View.Admin.AdminViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class WorkersDetailViewController {
    public TextField firstNameField;
    public TextField lastNameField;
    public Label errorLabel;
    public TextField pinField;
    private WorkersOverviewViewModel viewModel;
    private ViewHandler viewHandler;
    public BooleanProperty proceed;
    public void init(){
        viewHandler=ViewHandler.getInstance();
        viewModel= ViewModelFactory.getWorkersOverviewViewModel();
        firstNameField.textProperty().bindBidirectional(viewModel.firstNameProperty());
        lastNameField.textProperty().bindBidirectional(viewModel.lastNameProperty());
        pinField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null &&!newValue.matches("\\d*") ) {
                pinField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        pinField.textProperty().bindBidirectional(viewModel.pinProperty());
        errorLabel.textProperty().bindBidirectional(viewModel.errorProperty());
        proceed=new SimpleBooleanProperty();
        proceed.bind(viewModel.isAddedProperty());
    }

    public void addButton(ActionEvent actionEvent) {
        viewModel.addNewWorker();
        if(proceed.getValue())
            viewHandler.openManagerPane();
    }

    public void cancelButton(ActionEvent actionEvent) {
        viewModel.clearFields();
        viewHandler.openManagerPane();
    }
}
