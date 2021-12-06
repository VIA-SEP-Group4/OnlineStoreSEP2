package Model;

import Model.Models.Customer;
import Model.Models.Employee;
import Model.Models.Order;
import Model.Models.Product;
import Networking.LoginClient;
import Networking.ManagerClient;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class ManagerModelImpl implements ManagerModel, PropertyChangeListener {
    private PropertyChangeSupport support;
    private ManagerClient manager;
    private LoginClient login;
    private Employee loggedEmployee=null;

    public ManagerModelImpl(ManagerClient manager, LoginClient login) {
        this.manager = manager;
        this.login = login;
        manager.startClient();
        if(!login.isStarted()) login.startClient();

        support=new PropertyChangeSupport(this);
        manager.addListener("ManagerWorkersReply",this);
        manager.addListener("AddedWorker",this);
    }

    @Override
    public ArrayList<Order> getAllOrders() {
        return manager.getAllOrders();
    }

    @Override
    public ArrayList<Order> getWorkerOrdersForManager(int workerID) {
        return manager.getWorkerOrdersForManager(workerID);
    }

    @Override
    public ArrayList<Product> getProducts() {
        return manager.getAllProducts();
    }

    @Override
    public ArrayList<Employee> getWorkers() {
        return manager.getAllWorkers();
    }

    @Override
    public void addProduct(Product p) {
        manager.addProduct(p);
    }

    @Override
    public void deleteProduct(Product p) {
        manager.deleteProduct(p);
    }

    @Override
    public void addWorker(Employee employee) {
        manager.addWorker(employee);
    }

    @Override
    public void removeWorker(Employee employee) {
        manager.removeWorker(employee);
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
