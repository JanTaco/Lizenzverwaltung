package com.vodafone.spring_swlzfw;
 
import jakarta.persistence.*;
import java.util.List;
 
@Entity
public class EmployeeEntity {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
 
    private String email;
    private String department;
    private String company;
 
    // n:m Beziehung zu LicenseEntity mit Licensing als Join-Tabelle
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Licensing> licenses;
 
    // Getter und Setter
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getDepartment() {
        return department;
    }
 
    public void setDepartment(String department) {
        this.department = department;
    }
 
    public String getCompany() {
        return company;
    }
 
    public void setCompany(String company) {
        this.company = company;
    }
 
    public List<Licensing> getLicenses() {
        return licenses;
    }
 
    public void setLicenses(List<Licensing> licenses) {
        this.licenses = licenses;
    }
}