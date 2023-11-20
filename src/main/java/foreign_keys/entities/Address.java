package foreign_keys.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private long id;

    @Column(nullable = false, length = 50)
    private String line1;

    @Column(nullable = true, length = 50)
    private String line2;

    @Column(nullable = true, length = 50)
    private String line3;

    @Column(nullable = false, length = 10)
    private String postCode;

    @Column(nullable = false, length = 50)
    private String country;

    @OneToOne(mappedBy = "address")
    private Employee resident;

    public Address() {
    }

    public Address(long id, String line1, String line2, String line3, String postCode, String country, Employee resident) {
        this.id = id;
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.postCode = postCode;
        this.country = country;
        this.resident = resident;
    }

    public Address(String line1, String line2, String line3, String postCode, String country, Employee resident) {
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.postCode = postCode;
        this.country = country;
        this.resident = resident;
    }

    public Address(String line1, String postCode, String country, Employee resident) {
        this.line1 = line1;
        this.postCode = postCode;
        this.country = country;
        this.resident = resident;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine3() {
        return line3;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Employee getResident() {
        return resident;
    }

    public void setResident(Employee resident) {
        this.resident = resident;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return id == address.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        String residentName = "None";
        if(resident != null){
            residentName = resident.getfName() + " " + resident.getlName();
        }
        return "Address{" +
                "id=" + id +
                ", line1='" + line1 + '\'' +
                ", line2='" + line2 + '\'' +
                ", line3='" + line3 + '\'' +
                ", postCode='" + postCode + '\'' +
                ", country='" + country + '\'' +
                ", resident=" + residentName +
                '}';
    }

    public boolean update(Address a){
        if(this.equals(a)) {
            this.line1 = a.line1;
            this.line2 = a.line2;
            this.line3 = a.line3;
            this.postCode = a.postCode;
            this.country = a.country;
            this.resident = a.resident;
            return true;
        }
        return false;
    }
}
