package Model;

import Model.Models.Employee;
import Model.Models.Order;
import Networking.WorkerClient;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class WorkerModelImpl implements WorkerModel {
    public WorkerModelImpl(WorkerClient workerClient) {
    }

    @Override
    public ArrayList<Order> fetchWorkerOrders() {
        return null;
    }

    @Override
    public void changeOrderStatus(Order order) {

    }

    @Override
    public Employee getLoggedEmployee() {
        return null;
    }

    @Override
    public void addListener(String eventName, PropertyChangeListener listener) {

    }

    @Override
    public void removeListener(String eventName, PropertyChangeListener listener) {

    }
}
