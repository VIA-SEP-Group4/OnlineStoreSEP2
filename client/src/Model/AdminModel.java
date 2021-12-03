package Model;

import Utils.Subject;

import java.util.ArrayList;

public interface AdminModel extends Subject {
    ArrayList<Employee> getManagerEmployees();
}
