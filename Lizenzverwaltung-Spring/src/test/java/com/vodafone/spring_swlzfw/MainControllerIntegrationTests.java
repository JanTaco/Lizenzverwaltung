package com.vodafone.spring_swlzfw;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MainControllerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

}


//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class EmployeeControllerIntegrationTest {
//
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Test
//    public void testAddEntity() {
//        // Arrange
//        String email = "test@example.com";
//        String department = "IT";
//        String company = "Acme Corp";
//        String license = "123";
//
//        // Act
//        String url = "http://localhost:" + port + "/add/" + email + "/" + department + "/" + company + "/" + license;
//        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Saved", response.getBody());
//    }
//}