package foreign_keys.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="employees")
public class Employee {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(length=50, nullable = false, unique=true)
    private String email;

    private String fName;
    private String lName;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate startDate;

    // MANY Employees belong to ONE department
    @ManyToOne
    // Because this is the "owning" side, it gets the joining column
    // In other words, because this is the one that would have the foreign key in the database
    // it gets the join column
    // The join column is the foreign key value that links to the other table
    @JoinColumn(name="department")
    private Department department;

    // ONE Address belongs to ONE Employee
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToMany(mappedBy = "team")
    private Set<Project> projects;

    public Employee() {
    }

    public Employee(long id){
        this.id = id;
        this.projects = new HashSet<>();
    }

    public Employee(String email, String fName, String lName, LocalDate startDate, Department department, Address address, Set<Project> projects) {
        this.email = email;
        this.fName = fName;
        this.lName = lName;
        this.startDate = startDate;
        this.department = department;
        this.address = address;
        this.projects = projects;
    }

    public Employee(long id, String email, String fName, String lName, LocalDate startDate, Department department, Address address, Set<Project> projects) {
        this.id = id;
        this.email = email;
        this.fName = fName;
        this.lName = lName;
        this.startDate = startDate;
        this.department = department;
        this.address = address;
        this.projects = projects;
    }

    public Employee(String email, String fName, String lName, LocalDate startDate) {
        this.email = email;
        this.fName = fName;
        this.lName = lName;
        this.startDate = startDate;
        this.projects = new HashSet<>();
    }

    public Employee(String email, String fName, String lName, LocalDate startDate, Department department) {
        this.email = email;
        this.fName = fName;
        this.lName = lName;
        this.startDate = startDate;
        this.department = department;
        this.projects = new HashSet<>();
    }

    public Employee(String fName, String lName, LocalDate startDate) {
        this.fName = fName;
        this.lName = lName;
        this.startDate = startDate;
        this.projects = new HashSet<>();
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        if(startDate == null){
            throw new NullPointerException("Start date cannot be a null field");
        }
        this.startDate = startDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email == null){
            throw new NullPointerException("Email cannot be a null field");
        }
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        if(id <= 0){
            throw new IllegalArgumentException("Id values must be greater than 0");
        }
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department dept) {
        this.department = dept;
    }

    public Address getAddress() {
        return address;
    }

    // Needed for synchronization of data in bidirectional relationships
    public void setAddress(Address address) {
        if (this.address != null) {
            this.address.setResident(null);
        }
        if(address != null){
            address.setResident(this);
        }
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Helper method to fill in the toString with linked entity data (rather than the complete toString of
    // the other entity)
    private String getProjectNames(){
        if(projects.isEmpty()){
            return "[None]";
        }
        Iterator<Project> iter = projects.iterator();
        Project project = iter.next();
        String names = "[" + project.getName();
        while(iter.hasNext()){
            project = iter.next();
            names = names + ", " + project.getName();
        }

        return names+ "]";
    }

    // Helper method to fill in the toString with limited linked entity data (rather than the complete toString of
    // the other entity)
    private String getDeptName(){
        if(this.department != null){
            return this.department.getName();
        }
        return "[None]";
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", startDate=" + startDate +
                ", department=" + getDeptName() +
                ", address=" + address +
                ", projects=" + getProjectNames() +
                '}';
    }

    // As this is not the "owning" side of the relationship, we don't enforce referential integrity here
    // This does mean we are relying on the developer to maintain them correctly via the "owning" side
    public void addProject(Project p){
        this.projects.add(p);
    }

    public void removeProject(Project p){
        this.projects.remove(p);
    }
}
