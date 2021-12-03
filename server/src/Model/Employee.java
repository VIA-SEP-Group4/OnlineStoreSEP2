package Model;

import java.io.Serializable;

public class Employee implements Serializable {
    private String firstName;
    private String lastName;
    private int pin;
    private int ID=-1;
    private EmployeeType type;
    public enum EmployeeType{
        ADMIN,
        MANAGER,
        WORKER
    }
    public Employee(String firstName, String lastName, int pin, EmployeeType type, int ID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pin = pin;
        this.type=type;
        this.ID=ID;
    }

    public EmployeeType getType() {
        return type;
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
}
