package main;

import dao.ICarLeaseRepository;
import dao.ICarLeaseRepositoryImpl;
import entity.Customer;
import entity.Lease;
import entity.Payment;
import entity.Vehicle;
import exception.CarNotFoundException;
import exception.CustomerNotFoundException;
import exception.LeaseNotFoundException;
import util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Establish Database Connection
        Connection connection = DBConnection.getConnection();
        if (connection == null) {
            System.out.println("Database connection failed. Exiting...");
            return;
        }

        // Create repository instance
        ICarLeaseRepository carLeaseRepo = new ICarLeaseRepositoryImpl();
        ICarLeaseRepository leaseRepository = new ICarLeaseRepositoryImpl();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Car Rental System =====");
            System.out.println("1. Add a Car");
            System.out.println("2. Remove a Car");
            System.out.println("3. List Available Cars");
            System.out.println("4. Find Car by ID");
            System.out.println("5. Add a Customer");
            System.out.println("6. Remove a Customer");
            System.out.println("7. Find Customer by ID");
            System.out.println("8. Create a Lease");
            System.out.println("9. Return a Car");
            System.out.println("10. View Active Leases");
            System.out.println("11. View Lease History");
            System.out.println("12. Make Payment");  
            System.out.println("13. View Payment History");
            System.out.println("14. Find Payment by ID");
            System.out.println("15. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();                         // Consume newline

            try {
                switch (choice) {
                    case 1: // Add Car
                        System.out.print("Enter Car Make: ");
                        String make = scanner.nextLine();
                        System.out.print("Enter Car Model: ");
                        String model = scanner.nextLine();
                        System.out.print("Enter Year: ");
                        int year = scanner.nextInt();
                        System.out.print("Enter Daily Rate: ");
                        double rate = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter Status (available/notAvailable): ");
                        String status = scanner.nextLine();
                        System.out.print("Enter Passenger Capacity: ");
                        int passengerCapacity = scanner.nextInt();
                        System.out.print("Enter Engine Capacity: ");
                        double engineCapacity = scanner.nextDouble();

                        Vehicle newCar = new Vehicle(0, make, model, year, rate, status, passengerCapacity, engineCapacity);
                        carLeaseRepo.addCar(newCar);
                        System.out.println("Car added successfully!");
                        break;

                        
                    case 2: // Remove Car
                        System.out.print("Enter Car ID to remove: ");
                        int carID = scanner.nextInt();
                        carLeaseRepo.removeCar(carID);
                        System.out.println("Car removed successfully!");
                        break;

                        
                    case 3: // List Available Cars
                        List<Vehicle> availableCars = carLeaseRepo.listAvailableCars();
                        System.out.println("\nAvailable Cars:");
                        availableCars.forEach(System.out::println);
                        break;

                        
                    case 4: // Find Car by ID
                        System.out.print("Enter Car ID: ");
                        int searchCarID = scanner.nextInt();
                        Vehicle car = carLeaseRepo.findCarById(searchCarID);
                        System.out.println("Car Found: " + car);
                        break;

                        
                    case 5: // Add Customer
                        System.out.print("Enter First Name: ");
                        String firstName = scanner.nextLine();
                        System.out.print("Enter Last Name: ");
                        String lastName = scanner.nextLine();
                        System.out.print("Enter Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Enter Phone Number: ");
                        String phone = scanner.nextLine();

                        Customer newCustomer = new Customer(0, firstName, lastName, email, phone);
                        carLeaseRepo.addCustomer(newCustomer);
                        System.out.println("Customer added successfully!");
                        break;

                        
                    case 6: // Remove Customer
                        System.out.print("Enter Customer ID to remove: ");
                        int customerID = scanner.nextInt();
                        carLeaseRepo.removeCustomer(customerID);
                        System.out.println("Customer removed successfully!");
                        break;

                        
                    case 7: // Find Customer by ID
                        System.out.print("Enter Customer ID: ");
                        int searchCustomerID = scanner.nextInt();
                        Customer customer = carLeaseRepo.findCustomerById(searchCustomerID);
                        System.out.println("Customer Found: " + customer);
                        break;

                        
                    case 8: // Create Lease
                        System.out.print("Enter Customer ID: ");
                        int leaseCustomerID = scanner.nextInt();
                        System.out.print("Enter Car ID: ");
                        int leaseCarID = scanner.nextInt();
                        System.out.print("Enter Start Date (YYYY-MM-DD): ");
                        Date startDate = Date.valueOf(scanner.next());
                        System.out.print("Enter End Date (YYYY-MM-DD): ");
                        Date endDate = Date.valueOf(scanner.next());

                        Lease lease = carLeaseRepo.createLease(leaseCustomerID, leaseCarID, startDate, endDate);
                        System.out.println("Lease created successfully! Lease ID: " + lease.getLeaseID());
                        break;
                        

                    case 9: // Return Car
                        System.out.print("Enter Lease ID to return car: ");
                        int leaseID = scanner.nextInt();
                        carLeaseRepo.returnCar(leaseID);
                        System.out.println("Car returned successfully!");
                        break;

                        
                    case 10: // View Active Leases
                        System.out.println("\nActive Leases:");
                        List<Lease> activeLeases = leaseRepository.listActiveLeases();

                        if (activeLeases.isEmpty()) {
                            System.out.println("No active leases found!");
                        } else {
                            for (Lease lease1 : activeLeases) {
                                System.out.println(lease1);
                            }
                        }
                        break;

                        

                    case 11: // View Lease History
                    	System.out.println("\nLease History:");
                    	List<Lease> leaseHistory = leaseRepository.listLeaseHistory();
                    	for (Lease lease1 : leaseHistory) {
                    	    System.out.println(lease1);
                    	}
                        

                    case 12: // Make Payment
                        System.out.print("Enter Lease ID for Payment: ");
                        int paymentLeaseID = scanner.nextInt();
                        System.out.print("Enter Amount: ");
                        double amount = scanner.nextDouble();
                        System.out.print("Enter Payment Date (YYYY-MM-DD): ");
                        Date paymentDate = Date.valueOf(scanner.next());

                        Payment newPayment = new Payment(0, paymentLeaseID, paymentDate, amount);
                        carLeaseRepo.makePayment(newPayment);
                        System.out.println("Payment successful!");
                        break;

                        
                    case 13: // View Payment History
                        List<Payment> paymentHistory = carLeaseRepo.listPayments();
                        System.out.println("\nPayment History:");
                        paymentHistory.forEach(System.out::println);
                        break;

                        
                    case 14: // Find Payment by ID
                        System.out.print("Enter Payment ID: ");
                        int paymentID = scanner.nextInt();

                        Payment payment = carLeaseRepo.findPaymentById(paymentID);

                        if (payment != null) {
                            System.out.println("Payment Details:\n" + payment);  
                        } else {
                            System.out.println("❌ Payment not found.");
                        }
                        break;


                        

                    case 15: // Exit
                        System.out.println("Exiting...");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid choice! Please enter a valid option.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
}
