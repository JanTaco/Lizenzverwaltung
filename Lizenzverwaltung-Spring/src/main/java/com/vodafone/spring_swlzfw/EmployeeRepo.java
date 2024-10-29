package com.vodafone.spring_swlzfw;
 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
 
import java.util.List;
import java.util.Optional;
 
public interface EmployeeRepo extends CrudRepository<EmployeeEntity, Integer> {
 
    // Query zur Rückgabe von Mitarbeiterinformationen und den zugehörigen Lizenzinformationen über die Licensing-Tabelle
    @Query(value = "SELECT e.email, e.department, e.company, l.subscription_pack, l.expiration_date, l.purchase_order, l.purchase_order_originally " +
            "FROM employee_entity e " +
            "JOIN licensing lic ON e.id = lic.employee_id " +
            "JOIN license_entity l ON lic.license_id = l.id", nativeQuery = true)
    public List<Object> getAll();
 
    // Suchanfrage, um nach einem Begriff in verschiedenen Feldern zu suchen (über Licensing-Tabelle)
    @Query(value = "SELECT e.email, e.company, e.department, l.subscription_pack, l.expiration_date, l.purchase_order, l.purchase_order_originally " +
            "FROM employee_entity e " +
            "JOIN licensing lic ON e.id = lic.employee_id " +
            "JOIN license_entity l ON lic.license_id = l.id " +
            "WHERE e.email LIKE %:search% " +
            "OR e.department LIKE %:search% " +
            "OR e.company LIKE %:search% " +
            "OR l.purchase_order LIKE %:search% " +
            "OR l.purchase_order_originally LIKE %:search% " +
            "OR l.subscription_pack LIKE %:search% " +
            "OR l.expiration_date LIKE %:search%", nativeQuery = true)
    public List<Object> find(@Param("search") String search);
 
    @Query(value = "SELECT * FROM employee_entity Where email = :email AND department = :department AND company = :company", nativeQuery = true)
    public Optional<EmployeeEntity> searchEmployee(@Param("email") String email, @Param("department") String department, @Param("company") String company);
 
}
 