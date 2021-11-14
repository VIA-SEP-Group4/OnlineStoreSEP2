package View;

import Core.ViewHandler;
import Core.ViewModelFactory;

public interface ViewController
{
    void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory);
}
