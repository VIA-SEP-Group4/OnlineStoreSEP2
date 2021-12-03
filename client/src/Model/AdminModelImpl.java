package Model;

import Networking.AdminClient;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class AdminModelImpl implements AdminModel {
    private PropertyChangeSupport support;
    private AdminClient client;
    public AdminModelImpl(AdminClient client) {
        this.client=client;
        client.startClient();
        support=new PropertyChangeSupport(this);
    }

    @Override
    public void addListener(String eventName, PropertyChangeListener listener) {

    }

    @Override
    public void removeListener(String eventName, PropertyChangeListener listener) {

    }

    @Override
    public ArrayList<Employee> getManagerEmployees() {
        return client.getManagers();
    }
}
