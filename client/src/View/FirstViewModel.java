package View;

import Model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FirstViewModel
{
    private StringProperty request;
    private StringProperty reply;
    private StringProperty error;
    private Model model;

    public FirstViewModel(Model model){
        request = new SimpleStringProperty();
        reply = new SimpleStringProperty();
        error = new SimpleStringProperty();
        this.model = model;
    }

    public void convert(){
        model.toUpperCase(request.getValue());
    }

    public StringProperty getRequest(){
        return request;
    }

    public StringProperty getError()
    {
        return error;
    }

    public StringProperty getReply()
    {
        return reply;
    }
}
