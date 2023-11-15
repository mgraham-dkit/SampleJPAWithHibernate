package composite_keys.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="clients")
@IdClass(ClientId.class)
public class Client {
    @Id
    @Column(nullable = false, length=50)
    private String repName;

    @Id
    @Column(nullable = false, length = 75)
    private String companyName;

    @Column(nullable = false)
    private int salesRep;

    private double creditLimit;

    public Client() {
    }

    public Client(String repName, String companyName, int salesRep, double creditLimit) {
        this.repName = repName;
        this.companyName = companyName;
        this.salesRep = salesRep;
        this.creditLimit = creditLimit;
    }

    public String getRepName() {
        return repName;
    }

    public void setRepName(String repName) {
        this.repName = repName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getSalesRep() {
        return salesRep;
    }

    public void setSalesRep(int salesRep) {
        this.salesRep = salesRep;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(repName, client.repName) && Objects.equals(companyName, client.companyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(repName, companyName);
    }

    @Override
    public String toString() {
        return "Client{" +
                "repName='" + repName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", salesRep=" + salesRep +
                ", creditLimit=" + creditLimit +
                '}';
    }
}
