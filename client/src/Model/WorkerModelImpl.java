package Model;

import Model.Models.Employee;
import Model.Models.Order;
import Networking.LoginClient;
import Networking.WorkerClient;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class WorkerModelImpl implements WorkerModel,PropertyChangeListener {
    private WorkerClient workerClient;
    private LoginClient loginClient;
    private PropertyChangeSupport support;
    private Employee loggedEmployee=null;

    public WorkerModelImpl(WorkerClient workerClient,LoginClient loginClient) {
        this.workerClient=workerClient;
        this.loginClient = loginClient;
//        worker.startClient();
        if(!workerClient.isStarted()) workerClient.startClient();
//        login.startClient();
        if(!loginClient.isStarted()) loginClient.startClient();
        support=new PropertyChangeSupport(this);
    }

    @Override
    public ArrayList<Order> fetchWorkerOrders() {
        return null;
    }

    @Override
    public void changeOrderStatus(Order order) {

    }

    @Override public void changeOrderAssignee(Order order)
    {
        order.setWorkerID(getLoggedEmployee().getID());
        workerClient.changeOrderAssignee(order);
    }

    @Override public ArrayList<Order> getAllOrders()
    {
        return workerClient.getAllOrders();
    }

    @Override
    public Employee getLoggedEmployee() {
        loggedEmployee=loginClient.getLoggedEmployee();
        return loggedEmployee;
    }

    @Override
    public void addListener(String eventName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(eventName,listener);
    }

    @Override
    public void removeListener(String eventName, PropertyChangeListener listener) {
        support.removePropertyChangeListener(eventName,listener);
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        support.firePropertyChange(evt);
    }
}
