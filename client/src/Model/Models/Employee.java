package Model.Models;

import Enums.EmployeeType;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Employee implements Serializable {
    private String firstName;
    private String lastName;
    private int pin;
    private int ID;
    private EmployeeType type;
    private ArrayList<Order> openOrders;
    private ArrayList<Order> myOrders;

    @Serial private static final long serialVersionUID = 1L;

    public Employee(String firstName, String lastName, int pin, EmployeeType type,int ID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pin = pin;
        this.type=type;
        this.ID=ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getPin() {
        return pin;
    }

    public int getID() {
        return ID;
    }

    public EmployeeType getType() {
        return type;
    }
    public void setEmployeeId(int newEmployeeID) {
        this.ID=newEmployeeID;
    }

    public ArrayList<Order> getOpenOrders()
    {
        return openOrders;
    }
    public void setOpenOrders(ArrayList<Order> openOrders)
    {
        this.openOrders = openOrders;
    }

    public ArrayList<Order> getMyOrders()
    {
        return myOrders;
    }
    public void setMyOrders(ArrayList<Order> myOrders)
    {
        this.myOrders = myOrders;
    }

    public void removeFromOpenOrders(int orderId){
        openOrders.removeIf(o -> o.getOrderId() == orderId);
    }

    public void removeFromMyOrders(int orderId){
        myOrders.removeIf(o -> o.getOrderId() == orderId);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pin=" + pin +
                ", ID=" + ID +
                ", type=" + type +
                '}';
    }
}
