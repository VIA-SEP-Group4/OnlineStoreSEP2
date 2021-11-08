package Core;

import View.FirstViewModel;

public class ViewModelFactory {
    private FirstViewModel firstViewModel;

    public ViewModelFactory(ModelFactory modelFactory) {
        firstViewModel = new FirstViewModel(modelFactory.getManager());
    }

    public FirstViewModel getFirstViewModel() {
        return firstViewModel;
    }
}
