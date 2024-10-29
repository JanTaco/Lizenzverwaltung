package com.vodafone.spring_swlzfw;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTests {

    @MockBean
    private EmployeeRepo employeeRepo;
    @MockBean
    LicenseRepo licenseRepo;

    @Autowired
    private MockMvc mockMvc;

    //Employee Tests
    @Test
    void testAddEntity() throws Exception {
        String email = "JUnit@Test.mock";
        String department = "TestDepartment";
        String company = "TestCompany";
        String license = "1";

        // Create a sample EmployeeEntity
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setEmail(email);
        employeeEntity.setDepartment(department);
        employeeEntity.setCompany(company);
        employeeEntity.setLicense(Integer.parseInt(license));

        // Mock the save method of EmployeeRepo to return the sample entity
        when(employeeRepo.save(any(EmployeeEntity.class))).thenReturn(employeeEntity);

        // Perform the POST request
        mockMvc.perform(post("/add/{email}/{department}/{company}/{license}", email, department, company, license)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().string("Saved"));

        // Verify that the save method was called with the correct parameters
        verify(employeeRepo, times(1)).save(any(EmployeeEntity.class));
    }

    @Test
    void testEditEntityEmailNotChanged() throws Exception {
        String originalEmail = "JUnit@Test.mock";
        String email = "JUnit@Test.mock";
        String department = "UpdatedTestDepartment";
        String company = "UpdatedTestCompany";
        String license = "1";

        // Create a sample EmployeeEntity
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setEmail(email);
        employeeEntity.setDepartment(department);
        employeeEntity.setCompany(company);
        employeeEntity.setLicense(Integer.parseInt(license));

        // Mock the findById and save methods of EmployeeRepo
        when(employeeRepo.findById(originalEmail)).thenReturn(Optional.of(employeeEntity));
        when(employeeRepo.save(any(EmployeeEntity.class))).thenReturn(employeeEntity);

        // Perform the POST request

        mockMvc.perform(post("/update/{originalEmail}/{email}/{department}/{company}/{license}", originalEmail, email, department, company, license)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().string("Updated!"));

        // Verify that the findById and save methods were called with the correct parameters
        verify(employeeRepo, times(1)).findById(originalEmail);
        verify(employeeRepo, times(1)).save(any(EmployeeEntity.class));
        verify(employeeRepo, never()).deleteById(anyString());

    }

    @Test
    void testEditEntityEmailChanged() throws Exception {
        String originalEmail = "JUnit@Test.mock";
        String email = "JUnit2@Test.mock";
        String department = "UpdatedTestDepartment";
        String company = "UpdatedTestCompany";
        String license = "1";

        // Mock the deleteById method of EmployeeRepo
        doNothing().when(employeeRepo).deleteById(originalEmail);

        // Perform the POST request
        mockMvc.perform(post("/update/{originalEmail}/{email}/{department}/{company}/{license}", originalEmail, email, department, company, license)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string("Updated!"));

        // Verify that the deleteById and addEntity methods were called with the correct parameters
        verify(employeeRepo, times(1)).deleteById(originalEmail);
        verify(employeeRepo, times(1)).save(any(EmployeeEntity.class));
    }

    @Test
    void testDeleteEntity() throws Exception {
        String email = "JUnit@Test.mock";

        doNothing().when(employeeRepo).deleteById(email);
        mockMvc.perform(post("/delete/{email}", email)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string("Deleted"));
        verify(employeeRepo, times(1)).deleteById(email);
    }


    // License Tests

    @Test
    void testAddLicense() throws Exception {
        int amount = 333;
        Date expirationDate = Date.valueOf("2023-09-01");
        int purchaseOrder = 666;
        int purchaseOrderOriginally = 999;
        String subscriptionPack = "TestSubscriptionPack";

        LicenseEntity licenseEntity = new LicenseEntity();
        licenseEntity.setAmount(amount);
        licenseEntity.setExpirationDate(expirationDate);
        licenseEntity.setPurchaseOrder(purchaseOrder);
        licenseEntity.setPurchaseOrderOriginally(purchaseOrderOriginally);
        licenseEntity.setSubscriptionPack(subscriptionPack);

        when(licenseRepo.save(any(LicenseEntity.class))).thenReturn(licenseEntity);

        mockMvc.perform(post("/addLicense/{amount}/{expirationDate}/{purchaseOrder}/{purchaseOrderOriginally}/{subscriptionPack}", amount, expirationDate, purchaseOrder, purchaseOrderOriginally, subscriptionPack)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().string("Saved"));

        verify(licenseRepo, times(1)).save(any(LicenseEntity.class));
    }

    @Test
    void testUpdateLicense() throws Exception {
        int ID = 0;
        int amount = 333;
        Date expirationDate = Date.valueOf("2023-09-01");
        int purchaseOrder = 666;
        int purchaseOrderOriginally = 999;
        String subscriptionPack = "TestSubscriptionPack";

        LicenseEntity licenseEntity = new LicenseEntity();
        licenseEntity.setAmount(amount);
        licenseEntity.setExpirationDate(expirationDate);
        licenseEntity.setPurchaseOrder(purchaseOrder);
        licenseEntity.setPurchaseOrderOriginally(purchaseOrderOriginally);
        licenseEntity.setSubscriptionPack(subscriptionPack);

        when(licenseRepo.findById(ID)).thenReturn(Optional.of(licenseEntity));
        when(licenseRepo.save(any(LicenseEntity.class))).thenReturn(licenseEntity);

        mockMvc.perform(post("/updateLicense/{ID}/{amount}/{expirationDate}/{purchaseOrder}/{purchaseOrderOriginally}/{subscriptionPack}", ID, amount, expirationDate, purchaseOrder, purchaseOrderOriginally, subscriptionPack)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().string("Updated!"));

        verify(licenseRepo, times(1)).findById(ID);
        verify(licenseRepo, times(1)).save(any(LicenseEntity.class));
    }

    @Test
    void testDeleteLicense() throws Exception {
        int ID = 7327;

        doNothing().when(licenseRepo).deleteById(ID);
        mockMvc.perform(post("/deleteLicense/{ID}", ID)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string("Deleted"));
        verify(licenseRepo, times(1)).deleteById(ID);
    }

    @Test
    void testGetFreeLicenses() throws Exception {
        mockMvc.perform(get("/getFreeLicenses")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
        verify(licenseRepo, times(1)).getFreeLicenses();
    }

    @Test
    void testGetLicenseStatistic() throws Exception {
        mockMvc.perform(get("/getLicenseStatistic")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
        verify(licenseRepo, times(1)).getLicenseStatistic();
    }

    @Test
    void testSearch() throws Exception {
        String data = "testSerach";
        mockMvc.perform(get("/search/{searchFor}", data)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
        verify(employeeRepo, times(1)).find("%" + data + "%");
    }

    @Test
    void testGetAllEntities() throws Exception {
        mockMvc.perform(get("/all")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
        verify(employeeRepo, times(1)).getAll();
    }

}
