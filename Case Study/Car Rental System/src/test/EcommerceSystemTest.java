package test;

import dao.ICarLeaseRepositoryImpl;
import entity.Vehicle;
import entity.Customer;
import entity.Lease;
import exception.CustomerNotFoundException;
import exception.DataNotFoundException;
import org.junit.jupiter.api.*;

import java.sql.Date;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CarLeaseSystemTest {

    private static ICarLeaseRepositoryImpl db;

    @BeforeAll                                                 
    static void setup() {
        db = new ICarLeaseRepositoryImpl();
    }

    @Test
    @DisplayName("Test: Car is created successfully")
    void testCarCreatedSuccessfully() {
    	Vehicle car = new Vehicle(0, "Tesla", "Model 3", 2024, 4500.0, "Available", 5, 2.5);
        assertDoesNotThrow(() -> db.addCar(car));
    }

    @Test
    @DisplayName("Test: Lease is created successfully")
    void testLeaseCreatedSuccessfully() {
        assertDoesNotThrow(() -> {
            Lease lease = db.createLease(1500, 207, Date.valueOf("2025-04-08"), Date.valueOf("2025-04-30"));
            assertNotNull(lease);
            System.out.println("Lease created successfully: " + lease.getLeaseID());
        });
    }
              


    @Test
    @DisplayName("Test: Lease is retrieved successfully")
    void testLeaseRetrievedSuccessfully() {
        assertDoesNotThrow(() -> {
            Lease lease = db.getLeaseById(1); // assuming lease with ID 1 exists
            assertNotNull(lease);
        });
    }

    @Test
    @DisplayName("Test: Exception thrown when Customer ID not found")
    void testCustomerIdNotFoundException() {
        CustomerNotFoundException thrown = assertThrows(CustomerNotFoundException.class, () -> {
            db.findCustomerById(9999); // Make sure this ID really does NOT exist
        });
        assertTrue(thrown.getMessage().contains("Customer with ID"));
    }


    @Test
    @DisplayName("Test: Exception thrown when Car ID not found")
    void testCarIdNotFoundException() {
        DataNotFoundException thrown = assertThrows(DataNotFoundException.class, () -> {
            db.findCarById(9999); // ID not in database
        });
        assertTrue(thrown.getMessage().contains("Car not found"));
    }

    @Test
    @DisplayName("Test: Exception thrown when Lease ID not found")
    void testLeaseIdNotFoundException() {
        DataNotFoundException thrown = assertThrows(DataNotFoundException.class, () -> {
            db.getLeaseById(9999); // ID not in database
        });
        assertTrue(thrown.getMessage().contains("Lease not found"));
    }
}
