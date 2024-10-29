package com.vodafone.spring_swlzfw;
 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
 
import java.util.List;
 
public interface LicenseRepo extends CrudRepository<LicenseEntity, Integer> {
 
    // Abfrage zur Rückgabe freier Lizenzen basierend auf Licensing-Tabelle und Anzahl der zugewiesenen Mitarbeiter
    @Query(value = "SELECT l.id, l.amount, (l.amount - COALESCE(COUNT(lic.license_id), 0)) AS verfügbar, " +
            "l.expiration_date, l.purchase_order, l.purchase_order_originally, l.subscription_pack, l.start_date " +
            "FROM license_entity l " +
            "LEFT JOIN licensing lic ON l.id = lic.license_id " +
            "GROUP BY l.id, l.amount, l.expiration_date, l.purchase_order, l.purchase_order_originally, l.subscription_pack, l.start_date " +
            "HAVING COALESCE(COUNT(lic.license_id), 0) < l.amount", nativeQuery = true)
    public List<Object> getFreeLicenses();
 
    // Abfrage zur Rückgabe aller Lizenzen (freier und belegter Lizenzen)
    @Query(value = "SELECT l.id, l.amount, (l.amount - COALESCE(COUNT(lic.license_id), 0)) AS verfügbar, " +
            "l.expiration_date, l.purchase_order, l.purchase_order_originally, l.subscription_pack, l.start_date " +
            "FROM license_entity l " +
            "LEFT JOIN licensing lic ON l.id = lic.license_id " +
            "GROUP BY l.id, l.amount, l.expiration_date, l.purchase_order, l.purchase_order_originally, l.subscription_pack, l.start_date", nativeQuery = true)
    public List<Object> getAllLicenses();
 
    // Abfrage zur Berechnung der Lizenzstatistik (freie, belegte und Gesamtzahl)
    @Query(value = "SELECT t1.available AS frei, t2.unavailable AS belegt, t1.available + t2.unavailable AS gesamt " +
            "FROM (SELECT SUM(e1.available) as available " +
            "      FROM (SELECT (l.amount - COALESCE(COUNT(lic.license_id), 0)) AS available " +
            "            FROM license_entity l " +
            "            LEFT JOIN licensing lic ON l.id = lic.license_id " +
            "            GROUP BY l.id, l.amount, l.expiration_date, l.purchase_order, l.purchase_order_originally, l.subscription_pack " +
            "            HAVING COALESCE(COUNT(lic.license_id), 0) <= l.amount) AS e1) AS t1, " +
            "     (SELECT (COUNT(lic.license_id)) AS unavailable " +
            "      FROM licensing lic) AS t2", nativeQuery = true)
    public List<Object> getLicenseStatistic();
}