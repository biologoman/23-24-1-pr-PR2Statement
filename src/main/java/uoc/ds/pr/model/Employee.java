package uoc.ds.pr.model;

import edu.uoc.ds.adt.helpers.Position;
import edu.uoc.ds.adt.sequential.List;
import edu.uoc.ds.traversal.Traversal;

import java.time.LocalDate;

public class Employee {
    private String employeeId;
    private String name;
    private String surname;
    private LocalDate birthday;
    private Role role;

    public Employee(String employeeId, String name, String surname, LocalDate birthday, Role role){
        this.employeeId = employeeId;
        this.name = name;
        this. surname = surname;
        this.birthday = birthday;
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public Role getRole() {
        return role;
    }

    public String getSurname() {
        return surname;
    }

    //PR2
    public void addRole(Role role) {
        setRole(role);
        role.addEmployee(this);
    }

    public static void removeEmployee(List<Employee> employees, Employee employee) {
        Traversal<Employee> r = employees.positions();
        boolean found = false;
        Position<Employee> p = null;

        while (!found && r.hasNext()) {
            p = r.next();
            found = p.getElem().is(employee);
        }

        if (found) employees.delete(p);
    }
    public boolean is(Employee employee) {
        return this.employeeId.equals(employee.getEmployeeId());
    }
}
