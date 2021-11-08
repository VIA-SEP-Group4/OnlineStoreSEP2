package View;

import Core.ViewHandler;
import Core.ViewModelFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FirstViewController implements ViewController
{

    @FXML
    private TextField requestField;
    @FXML
    private TextField replyField;
    @FXML
    private Label errorLabel;

    private FirstViewModel viewModel;
    private ViewHandler viewHandler;

    @Override public void init(ViewHandler viewHandler, FirstViewModel viewModel)
    {
        this.viewModel = viewModel;
        errorLabel.textProperty().bind(viewModel.getError());
        requestField.textProperty().bindBidirectional(viewModel.getRequest());
        replyField.textProperty().bind(viewModel.getReply());
    }

    public void onSubmitButton(ActionEvent actionEvent)
    {
        System.out.println("Submit pressed.");
        viewModel.convert();
    }


}
