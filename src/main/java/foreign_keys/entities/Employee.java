package foreign_keys.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    public Employee() {
    }

    public Employee(long id){
        this.id = id;
    }

    public Employee(String email, String fName, String lName, LocalDate startDate, Department department, Address address) {
        this.email = email;
        this.fName = fName;
        this.lName = lName;
        this.startDate = startDate;
        this.department = department;
        this.address = address;
    }

    public Employee(long id, String email, String fName, String lName, LocalDate startDate, Department department, Address address) {
        this.id = id;
        this.email = email;
        this.fName = fName;
        this.lName = lName;
        this.startDate = startDate;
        this.department = department;
        this.address = address;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department dept) {
        this.department = dept;
    }

    public Employee(String email, String fName, String lName, LocalDate startDate) {
        this.email = email;
        this.fName = fName;
        this.lName = lName;
        this.startDate = startDate;
    }

    public Employee(String email, String fName, String lName, LocalDate startDate, Department department) {
        this.email = email;
        this.fName = fName;
        this.lName = lName;
        this.startDate = startDate;
        this.department = department;
    }

    public Employee(String fName, String lName, LocalDate startDate) {
        this.fName = fName;
        this.lName = lName;
        this.startDate = startDate;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
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

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", startDate=" + startDate +
                ", department=" + department +
                ", address=" + address +
                '}';
    }
}
