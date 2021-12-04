package Model;

import Networking.AdminClient;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class AdminModelImpl implements AdminModel,PropertyChangeListener {
    private PropertyChangeSupport support;
    private AdminClient client;
    public AdminModelImpl(AdminClient client) {
        this.client=client;
        client.startClient();
        support=new PropertyChangeSupport(this);
        client.addListener("ManagerAddReply",this);
        client.addListener("AdminReply",this);
    }

    @Override
    public void addListener(String eventName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(eventName,listener);
    }

    @Override
    public void removeListener(String eventName, PropertyChangeListener listener) {
        support.removePropertyChangeListener(eventName,listener);
    }

    @Override
    public ArrayList<Employee> getManagerEmployees() {
        return client.getManagers();
    }

    @Override
    public void addManager(Employee manager) {
        client.addManager(manager);
    }

    @Override
    public void removeManager(Employee manager) {
        client.removeManager(manager);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println(evt.getNewValue());
        support.firePropertyChange(evt);
    }
}
