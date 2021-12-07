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
    private WorkerClient worker;
    private LoginClient login;
    private PropertyChangeSupport support;
    private Employee loggedEmployee=null;
    public WorkerModelImpl(WorkerClient workerClient,LoginClient login) {
        this.worker=workerClient;
        workerClient.startClient();
        if(!login.isStarted()) login.startClient();
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
        // TODO: 07/12/2021 get the client???
//        order.setWorkerID(getLoggedEmployee().getID());
        order.setWorkerID(3);
        worker.changeOrderAssignee(order);
    }

    @Override public ArrayList<Order> getAllOrders()
    {
        return worker.getAllOrders();
    }

    @Override
    public Employee getLoggedEmployee() {
        loggedEmployee=login.getLoggedEmployee();
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
