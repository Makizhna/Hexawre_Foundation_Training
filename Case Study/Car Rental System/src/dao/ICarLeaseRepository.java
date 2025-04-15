package dao;

import entity.Vehicle;
import exception.CarNotFoundException;
import exception.CustomerNotFoundException;
import exception.DataNotFoundException;
import exception.LeaseNotFoundException;
import entity.Customer;
import entity.Lease;
import entity.Payment;

import java.sql.SQLException;
import java.util.List;

public interface ICarLeaseRepository {
    // Car Management
    void addCar(Vehicle car);                            // Adds a new car
    void removeCar(int carID) throws CarNotFoundException; // Removes a car by ID
    List<Vehicle> listAvailableCars();                   // Lists all available cars
    List<Vehicle> listRentedCars();                      // Lists all rented cars
    Vehicle findCarById(int carId) throws DataNotFoundException;          // Finds a car by ID


    // Customer Management
    void addCustomer(Customer customer);                // Adds a new customer
    void removeCustomer(int customerID) throws CustomerNotFoundException; // Removes a customer by ID
    List<Customer> listCustomers();                     // Lists all customers
    Customer findCustomerById(int customerID) throws CustomerNotFoundException; // Finds a customer by ID

    // Lease Management
    Lease createLease(int customerID, int carID, java.sql.Date startDate, java.sql.Date endDate); // Creates a new lease
    void returnCar(int leaseID) throws LeaseNotFoundException; // Returns a car based on lease ID
    List<Lease> listActiveLeases();                    // Lists all active leases
    List<Lease> listLeaseHistory();                   // Lists all lease history
    Lease getLeaseById(int leaseId) throws DataNotFoundException;        //get lease by id
    
    
    // Payment Handling
    void makePayment(Payment payment);                // make  payment for a lease
    List<Payment> listPayments();                     // Retrieves all payments
    Payment findPaymentById(int paymentID) throws DataNotFoundException;      // Finds a payment by ID
    void recordPayment(Lease lease, double amount);         // records payment for a lease
	

}
