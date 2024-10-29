package com.vodafone.spring_swlzfw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Controller
public class MainController {
    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private LicenseRepo licenseRepo;

    @Autowired
    private  LicensingRepo licensingRepo;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path = "/addEmployee/{email}/{department}/{company}")

    public @ResponseBody String addEmployee(@PathVariable String email, @PathVariable String department, @PathVariable String company) {
        EmployeeEntity e = new EmployeeEntity();
        e.setEmail(email);
        e.setDepartment(department);
        e.setCompany(company);
        employeeRepo.save(e);
        return "Saved";
    }
/*
    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping(path = "/update/{email}/{department}/{company}")
    public @ResponseBody String editEntity(@PathVariable String email
            , @PathVariable String department, @PathVariable String company) {

            EmployeeEntity e = employeeRepo.findById(email).get();
            e.setDepartment(department);
            e.setCompany(company);
            //e.setLicense(Integer.parseInt(license));
            employeeRepo.save(e);


        return "Updated!";
    }*/

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping(path = "/updateEmployee/{email}/{department}/{company}/{originalEmail}/{originalDepartment}/{originalCompany}")
    public @ResponseBody String editEmployee(@PathVariable String email, @PathVariable String department, @PathVariable String company, @PathVariable String originalEmail, @PathVariable String originalDepartment, @PathVariable String originalCompany) {

        EmployeeEntity employee = employeeRepo.searchEmployee(originalEmail, originalDepartment, originalCompany).orElse(null);

        if (employee != null) {

            employee.setDepartment(department);
            employee.setCompany(company);
            employee.setEmail(email);
            employeeRepo.save(employee);
            System.out.println("Employee with email " + email + " found and updated.");
            return "Updated!";
        } else {
            System.out.println("Employee with email " + email + " not found.");
            return "Employee not found!";
        }
    }

    @GetMapping(path = "/test")
    public @ResponseBody String test() {


        return "LOLOLOL!";
    }



    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping(path = "/deleteEmployee/{id}")
    public @ResponseBody String deleteEmployee(@PathVariable int id) {

        employeeRepo.deleteById(id);
        return "Deleted";
    }





    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path = "/addLicense/{amount}/{expirationDate}/{purchaseOrder}/{purchaseOrderOriginally}/{subscriptionPack}/{startDate}")
    public @ResponseBody String addLicenseEntity(@PathVariable String amount, @PathVariable String expirationDate, @PathVariable String purchaseOrder, @PathVariable String purchaseOrderOriginally, @PathVariable String subscriptionPack, @PathVariable String startDate) {
        LicenseEntity l = new LicenseEntity();
        l.setAmount(Integer.parseInt(amount));
        l.setExpirationDate(Date.valueOf(expirationDate).toLocalDate());
        l.setPurchaseOrder(Integer.parseInt(purchaseOrder));
        l.setPurchaseOrderOriginally(Integer.parseInt(purchaseOrderOriginally));
        l.setSubscriptionPack(subscriptionPack);
        l.setStartDate(Date.valueOf(startDate).toLocalDate());

        licenseRepo.save(l);
        return "Saved";
    }



    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path = "/addLicensing/{licenseID}/{employeeID}")
    public @ResponseBody String addLicensing(@PathVariable int licenseID, @PathVariable int employeeID) {

        EmployeeEntity employee = employeeRepo.findById(employeeID).orElse(null);
        LicenseEntity license = licenseRepo.findById(licenseID).orElse(null);

        if (employee == null && license == null){
            return  "license and employee not found";
        } else if (employee == null) {
            return  "employee not found";
        }else if (license == null) {
            return  "license not found";
        }


        Licensing l = new Licensing();
        l.setLicense(license);
        l.setEmployee(employee);


        licensingRepo.save(l);
        return "Saved";
    }

    //E-Mail Department Company (license)ID
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path = "/addLicensingWithFrontend/{email}/{department}/{company}/{licenseID}")
    public @ResponseBody String addLicensingWithFrontend(@PathVariable String email, @PathVariable String department, @PathVariable String company, @PathVariable int licenseID) {

        EmployeeEntity employee = employeeRepo.searchEmployee(email, department, company).orElse(null);
        LicenseEntity license = licenseRepo.findById(licenseID).orElse(null);

        if (license == null) {
            return  "license not found";
        }

        if (employee == null) {
            employee = new EmployeeEntity();
            employee.setEmail(email);
            employee.setCompany(company);
            employee.setDepartment(department);

            employeeRepo.save(employee);
        }

        Licensing l = new Licensing();
        l.setLicense(license);
        l.setEmployee(employee);



        licensingRepo.save(l);


        return "Saved";
    }


/*
    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping(path = "/updateLicense/{ID}/{amount}/{startDate}/{expirationDate}/{purchaseOrder}/{purchaseOrderOriginally}/{subscriptionPack}")
    public @ResponseBody String updateLicenseEntity(@PathVariable int ID, @PathVariable String amount, @PathVariable String startDate, @PathVariable String expirationDate, @PathVariable String purchaseOrder, @PathVariable String purchaseOrderOld, @PathVariable String subscriptionPack) {
        LicenseEntity l = licenseRepo.findById(ID).get();
        l.setAmount(Integer.parseInt(amount));
        l.setStartDate(Date.valueOf(startDate).toLocalDate());
        l.setExpirationDate(Date.valueOf(expirationDate).toLocalDate());
        l.setPurchaseOrder(Integer.parseInt(purchaseOrder));
        l.setPurchaseOrderOriginally(Integer.parseInt(purchaseOrderOld));
        l.setSubscriptionPack(subscriptionPack);

        licenseRepo.save(l);
        return "Updated!";
    }
*/

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping(path = "/updateLicense/{ID}/{amount}/{startDate}/{expirationDate}/{purchaseOrder}/{purchaseOrderOriginally}/{subscriptionPack}")
    public @ResponseBody ResponseEntity<String> updateLicenseEntity(
            @PathVariable int ID,
            @PathVariable String amount,
            @PathVariable String startDate,
            @PathVariable String expirationDate,
            @PathVariable String purchaseOrder,
            @PathVariable String purchaseOrderOriginally,
            @PathVariable String subscriptionPack) {

        try {
            // 1. Lizenz suchen
            Optional<LicenseEntity> licenseOptional = licenseRepo.findById(ID);

            if (!licenseOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("License with ID " + ID + " not found.");
            }

            LicenseEntity license = licenseOptional.get();


                license.setAmount(Integer.parseInt(amount));
                license.setStartDate(Date.valueOf(startDate).toLocalDate());
                license.setExpirationDate(Date.valueOf(expirationDate).toLocalDate());
                license.setPurchaseOrder(Integer.parseInt(purchaseOrder));
                license.setPurchaseOrderOriginally(Integer.parseInt(purchaseOrderOriginally));
                license.setSubscriptionPack(subscriptionPack);


            // 3. Lizenz speichern
            licenseRepo.save(license);

            // 4. Erfolgsnachricht
            return ResponseEntity.ok("License updated successfully!");

        } catch (Exception e) {
            // Fehlerprotokollierung und Fehlerantwort
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating license: " + e.getMessage());
        }
    }



    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping(path = "deleteLicense/{ID}")
    public @ResponseBody String deleteLicenseEntity(@PathVariable int ID) {
        licenseRepo.deleteById(ID);
        return "Deleted";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping(path = "deleteLicensing/{ID}")
    public @ResponseBody String deleteLicensing(@PathVariable int ID) {

        licensingRepo.deleteById(ID);
        return "Deleted";
    }


    //Hier werden alle daten ausgegeben, die in der Tabelle angezeigt werden wenn die Tabelle im Licensing mode ist 
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path = {"/all", "/getall", "/a", "/search/"})
    public @ResponseBody List<Object> getAllDataForLicensing() {
//    public @ResponseBody List<Map<String,Object>> getAllEntities() {
        return employeeRepo.getAll();
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path = {"/allLicensing"})
    public @ResponseBody List<List<Object>> getAllLicensing() {


        List<List<Object>> result = new ArrayList<>();


        List<Object[]> licensingEntries = licensingRepo.getAllLicensingEntries();


        for (Object[] entry : licensingEntries) {

            List<Object> entryData = new ArrayList<>();

            entryData.add(entry[1]);  // E-Mail
            entryData.add(entry[2]);  // department
            entryData.add(entry[3]);  // compamy
            entryData.add(entry[4]);  // Subscribtion pack
            entryData.add(entry[5]);  // Ablaufdatum der Lizenz
            entryData.add(entry[6]);  // Neue PO
            entryData.add(entry[7]);  // Alte PO
            entryData.add(entry[0]);  // Lizenz ID


            result.add(entryData);
        }


        return result;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path = {"/getFreeLicenses"})
    public @ResponseBody List<Object> getFreeLicenses() {
        return licenseRepo.getFreeLicenses();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path = {"/getAllLicenses"})
    public @ResponseBody List<Object> getAllLicenses() {
        return licenseRepo.getAllLicenses();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path = {"/getLicenseStatistic"})
    public @ResponseBody List<Object> getLicenseStatistic() {
        return licenseRepo.getLicenseStatistic();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path = "/search/{data}")
    public @ResponseBody List<Object> searchEntities(@PathVariable String data) {
        return employeeRepo.find("%" + data + "%");
    }


}
