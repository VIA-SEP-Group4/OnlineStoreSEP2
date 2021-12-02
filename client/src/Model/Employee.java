package Model;

import java.io.Serializable;

public class Employee implements Serializable {
    private String firstName;
    private String lastName;
    private int pin;
    private String username;
    private EmployeeType type;
    public enum EmployeeType{
        ADMIN,
        MANAGER,
        WORKER
    }
    public Employee(String firstName, String lastName, int pin, EmployeeType type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pin = pin;
        this.username="fos_"+firstName.substring(0,2)+lastName.substring(0,2);
        this.type=type;
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

    public String getUsername() {
        return username;
    }
}
