package composite_keys.entities;

import java.io.Serializable;
import java.util.Objects;

public class ClientId implements Serializable {
    private String repName;
    private String companyName;

    public ClientId() {
    }

    public ClientId(String repName, String companyName) {
        this.repName = repName;
        this.companyName = companyName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientId clientId = (ClientId) o;
        return Objects.equals(repName, clientId.repName) && Objects.equals(companyName, clientId.companyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(repName, companyName);
    }

    @Override
    public String toString() {
        return "ClientId{" +
                "repName='" + repName + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
