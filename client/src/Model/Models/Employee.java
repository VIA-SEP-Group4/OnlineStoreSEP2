package Model.Models;

import Enums.EmployeeType;

import java.io.Serial;
import java.io.Serializable;

public class Employee implements Serializable {
    private String firstName;
    private String lastName;
    private int pin;
    private int ID;
    private EmployeeType type;
    @Serial
    private static final long serialVersionUID = 1L;

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
