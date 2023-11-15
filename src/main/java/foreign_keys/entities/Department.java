package foreign_keys.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="departments")
public class Department {
    @Id
    private String name;
    @OneToMany
    @JoinColumn(name = "department")
    private List<Employee> employees;

    public Department() {
        employees = new ArrayList();
    }

    public Department(String name) {
        this.name = name;
        employees = new ArrayList();
    }

    public Department(String name, List<Employee> employees) {
        this.name = name;
        this.employees = employees;
        employees = new ArrayList();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> staff) {
        this.employees = staff;
    }

    public boolean addStaff(Employee e){
        if(!this.employees.contains(e)){
            this.employees.add(e);
            return true;
        }
        return false;
    }

    public boolean removeStaff(Employee e){
        return this.employees.remove(e);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        String emailList = "";
        for(Employee e : employees){
            emailList+=e.getEmail()+"\n";
        }
        return "Department{" +
                "name='" + name + '\'' + "\nstaffList=\n" + emailList +
                '}';
    }
}
