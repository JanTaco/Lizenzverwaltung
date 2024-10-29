package com.vodafone.spring_swlzfw;
 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
 
import java.util.List;
 
public interface LicensingRepo extends CrudRepository<Licensing, Integer> {
 
    // Abfrage aller Verknüpfungen zwischen Mitarbeitern und Lizenzen
    @Query(value = "SELECT lic.id, e.email, e.department, e.company, l.subscription_pack, l.expiration_date, l.purchase_order, l.purchase_order_originally " +
            "FROM licensing lic " +
            "JOIN employee_entity e ON lic.employee_id = e.id " +
            "JOIN license_entity l ON lic.license_id = l.id", nativeQuery = true)
    public List<Object[]> getAllLicensingEntries();  // Rückgabetyp anpassen
 
 
    // Abfrage, um alle Lizenzen für einen bestimmten Mitarbeiter zu finden
    @Query(value = "SELECT l.id, l.subscription_pack, l.expiration_date " +
            "FROM licensing lic " +
            "JOIN license_entity l ON lic.license_id = l.id " +
            "WHERE lic.employee_id = :employeeId", nativeQuery = true)
    public List<Object> findLicensesByEmployee(@Param("employeeId") int employeeId);
 
    // Abfrage, um alle Mitarbeiter zu finden, die eine bestimmte Lizenz verwenden
    @Query(value = "SELECT e.id, e.email, e.department " +
            "FROM licensing lic " +
            "JOIN employee_entity e ON lic.employee_id = e.id " +
            "WHERE lic.license_id = :licenseId", nativeQuery = true)
    public List<Object> findEmployeesByLicense(@Param("licenseId") int licenseId);
 
    @Query(value = "SELECT *" +
            "FROM licensing", nativeQuery = true)
    public List<Object> getAll();
}