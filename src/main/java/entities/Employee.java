package entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="employees")
public class Employee {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @Column(length=50, nullable = false, unique=true)
    private String email;

    private String fName;
    private String lName;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate startDate;

    public Employee() {
    }

    public Employee(long id){
        this.id = id;
    }

    public Employee(String email, String fName, String lName, LocalDate startDate) {
        this.email = email;
        this.fName = fName;
        this.lName = lName;
        this.startDate = startDate;
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

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", startDate=" + startDate +
                '}';
    }
}
