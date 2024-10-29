package com.vodafone.spring_swlzfw;
 
import jakarta.persistence.*;
 
import java.time.LocalDate;
import java.util.List;
 
@Entity
public class LicenseEntity {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
 
    private String subscriptionPack;
    private LocalDate expirationDate;
    private int purchaseOrder;
    private int purchaseOrderOriginally;
    private int amount;
    private LocalDate startDate;
 
    // n:m Beziehung zu EmployeeEntity mit Licensing als Join-Tabelle
    @OneToMany(mappedBy = "license", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Licensing> employees;
 
    // Getter und Setter
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public String getSubscriptionPack() {
        return subscriptionPack;
    }
 
    public void setSubscriptionPack(String subscriptionPack) {
        this.subscriptionPack = subscriptionPack;
    }
 
    public LocalDate getExpirationDate() {
        return expirationDate;
    }
 
    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
 
    public int getPurchaseOrder() {
        return purchaseOrder;
    }
 
    public void setPurchaseOrder(int purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }
 
    public int getPurchaseOrderOriginally() {
        return purchaseOrderOriginally;
    }
 
    public void setPurchaseOrderOriginally(int purchaseOrderOriginally) {
        this.purchaseOrderOriginally = purchaseOrderOriginally;
    }
 
    public int getAmount() {
        return amount;
    }
 
    public void setAmount(int amount) {
        this.amount = amount;
    }
 
    public LocalDate getStartDate() {
        return startDate;
    }
 
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
 
    public List<Licensing> getEmployees() {
        return employees;
    }
 
    public void setEmployees(List<Licensing> employees) {
        this.employees = employees;
    }
}