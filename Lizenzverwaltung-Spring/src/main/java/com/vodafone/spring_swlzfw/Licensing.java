package com.vodafone.spring_swlzfw;
 
import jakarta.persistence.*;
 
@Entity
public class Licensing {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
 
    // Viele Lizenzen können einem Mitarbeiter zugeordnet sein
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;
 
    // Viele Mitarbeiter können einer Lizenz zugeordnet sein
    @ManyToOne
    @JoinColumn(name = "license_id")
    private LicenseEntity license;
 
    // Getter und Setter
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public EmployeeEntity getEmployee() {
        return employee;
    }
 
    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }
 
    public LicenseEntity getLicense() {
        return license;
    }
 
    public void setLicense(LicenseEntity license) {
        this.license = license;
    }
}