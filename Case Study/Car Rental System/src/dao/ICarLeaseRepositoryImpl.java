package dao;

import entity.Vehicle;

import entity.Customer;
import entity.Lease;
import entity.Payment;
import exception.CarNotFoundException;
import exception.CustomerNotFoundException;
import exception.DataNotFoundException;
import exception.LeaseNotFoundException;
import util.DBConnUtil;

import java.sql.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ICarLeaseRepositoryImpl implements ICarLeaseRepository {

    private Connection connection;
    private static final String PROPERTY_FILE = "src/dbconfig.properties";

    // Constructor to establish the database connection
    public ICarLeaseRepositoryImpl() {                                      
    	    this.connection = util.DBConnUtil.getConnection(PROPERTY_FILE);

    }

    // Car Management
    @Override
    public void addCar(Vehicle vehicle) {
        String sql = "INSERT INTO vehicle (make, model, year, daily_rate, status, passenger_capacity, engine_capacity) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, vehicle.getMake());
            pstmt.setString(2, vehicle.getModel());
            pstmt.setInt(3, vehicle.getYear());
            pstmt.setDouble(4, vehicle.getDailyRate());
            pstmt.setString(5, vehicle.getStatus());
            pstmt.setInt(6, vehicle.getPassengerCapacity());
            pstmt.setDouble(7, vehicle.getEngineCapacity());
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        vehicle.setVehicleID(generatedKeys.getInt(1));
                        System.out.println("Vehicle added successfully! Assigned ID: " + vehicle.getVehicleID());
                    }
                }
            } else {
                System.out.println("Vehicle insertion failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Remove a car
    @Override
    public void removeCar(int carID) throws CarNotFoundException {
        String sql = "DELETE FROM vehicle WHERE vehicle_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, carID);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new CarNotFoundException("Car with ID " + carID + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // List available cars
    @Override
    public List<Vehicle> listAvailableCars() {
        List<Vehicle> availableCars = new ArrayList<>();
        String query = "SELECT * FROM vehicle WHERE status = 'available'";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                availableCars.add(mapCar(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableCars;
    }
    
    
    //List rented cars
    @Override
    public List<Vehicle> listRentedCars() {
        List<Vehicle> rentedCars = new ArrayList<>();
        String query = "SELECT * FROM Vehicle WHERE status = 'notAvailable'";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                rentedCars.add(mapCar(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentedCars;
    }


    @Override
    public Vehicle findCarById(int carId) throws DataNotFoundException {
        String query = "SELECT * FROM vehicle WHERE vehicle_id = ?";
        try (Connection conn = DBConnUtil.getConnection(PROPERTY_FILE);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, carId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                	return new Vehicle(
                		    rs.getInt("vehicle_id"),
                		    rs.getString("make"),
                		    rs.getString("model"),
                		    rs.getInt("year"),
                		    rs.getDouble("daily_rate"),
                		    rs.getString("status"),
                		    rs.getInt("passenger_capacity"),
                		    rs.getDouble("engine_capacity")
                		);

                } else {
                    throw new DataNotFoundException("Car not found");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error while retrieving car", e); 
        }
    }



    // Customer Management
    @Override
    public void addCustomer(Customer customer) {
        String query = "INSERT INTO customer (first_name, last_name, email, phone_number) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, customer.getfirstName());
            pstmt.setString(2, customer.getlastName());
            pstmt.setString(3, customer.getemail());
            pstmt.setString(4, customer.getphoneNumber());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    

    @Override
    public void removeCustomer(int customerID) throws CustomerNotFoundException {
        String query = "DELETE FROM customer WHERE customer_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, customerID);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new CustomerNotFoundException("Customer with ID " + customerID + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    

    @Override
    public List<Customer> listCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM Customer";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                customers.add(mapCustomer(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
    
    

    @Override
    public Customer findCustomerById(int customerID) throws CustomerNotFoundException {
        String query = "SELECT * FROM Customer WHERE customer_id = ?";
        try (Connection conn = DBConnUtil.getConnection("src/dbconfig.properties");
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, customerID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapCustomer(rs);
                } else {
                    throw new CustomerNotFoundException("Customer with ID " + customerID + " not found.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error while retrieving customer", e);
        }
    }

 


    // Lease Management
    
    @Override
    public Lease createLease(int customerID, int carID, Date startDate, Date endDate) {
        // Step 1: Define lease type and fetch rate
        String leaseType = "Daily";  

        double dailyRate = 0.0;
        try {
            // Get the car's daily rate from the database
            String rateQuery = "SELECT daily_rate FROM vehicle WHERE vehicle_id = ?";
            try (PreparedStatement rateStmt = connection.prepareStatement(rateQuery)) {
                rateStmt.setInt(1, carID);
                try (ResultSet rs = rateStmt.executeQuery()) {
                    if (rs.next()) {
                        dailyRate = rs.getDouble("daily_rate");
                    } else {
                        System.out.println("Car not found for rate calculation.");
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        // Step 2: Calculate duration and cost
        long days = ChronoUnit.DAYS.between(startDate.toLocalDate(), endDate.toLocalDate());
        double totalCost = days * dailyRate;

        // Step 3: Insert into lease table including total cost
        String query = "INSERT INTO lease (customer_id, vehicle_id, start_date, end_date, type, total_cost) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, customerID);
            pstmt.setInt(2, carID);
            pstmt.setDate(3, startDate);
            pstmt.setDate(4, endDate);
            pstmt.setString(5, leaseType);
            pstmt.setDouble(6, totalCost);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int leaseID = generatedKeys.getInt(1);

                        // Update vehicle status to 'notAvailable'
                        String updateStatus = "UPDATE vehicle SET status = 'notAvailable' WHERE vehicle_id = ?";
                        try (PreparedStatement statusStmt = connection.prepareStatement(updateStatus)) {
                            statusStmt.setInt(1, carID);
                            statusStmt.executeUpdate();
                        }

                        return new Lease(leaseID, customerID, carID, startDate, endDate, leaseType, totalCost);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    
    


    @Override
    public void returnCar(int leaseID) throws LeaseNotFoundException {
        String carQuery = "UPDATE vehicle SET status = 'available' WHERE vehicle_id = (SELECT vehicle_id FROM lease WHERE lease_id = ?)";
        String leaseQuery = "DELETE FROM lease WHERE lease_id = ?";

        try (PreparedStatement carStmt = connection.prepareStatement(carQuery);
             PreparedStatement leaseStmt = connection.prepareStatement(leaseQuery)) {

            carStmt.setInt(1, leaseID);
            leaseStmt.setInt(1, leaseID);

            int carUpdated = carStmt.executeUpdate();
            int leaseDeleted = leaseStmt.executeUpdate();

            if (leaseDeleted == 0) {
                throw new LeaseNotFoundException("Lease with ID " + leaseID + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    

    @Override
    public List<Lease> listActiveLeases() {
        List<Lease> activeLeases = new ArrayList<>();
        String query = "SELECT * FROM Lease WHERE end_date > CURDATE()"; 

        try (Connection conn = DBConnUtil.getConnection(PROPERTY_FILE); 
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            boolean found = false;

            while (rs.next()) {
                found = true;

                int leaseId = rs.getInt("lease_id");
                int vehicleId = rs.getInt("vehicle_id");
                int customerId = rs.getInt("customer_id");
                Date startDate = rs.getDate("start_date");
                Date endDate = rs.getDate("end_date");
                String leaseType = rs.getString("type");

                // Create Lease object
                Lease lease = new Lease(leaseId, vehicleId, customerId, startDate, endDate, leaseType, 0.0);

                // 🔹 Get daily_rate from vehicle table
                double dailyRate = 0.0;
                double monthlyRate = 1000.0; // Fallback monthly rate

                try (PreparedStatement rateStmt = conn.prepareStatement("SELECT daily_rate FROM vehicle WHERE vehicle_id = ?")) {
                    rateStmt.setInt(1, vehicleId);
                    try (ResultSet rateRs = rateStmt.executeQuery()) {
                        if (rateRs.next()) {
                            dailyRate = rateRs.getDouble("daily_rate");
                        }
                    }
                }

                // 🔹 Calculate cost
                lease.calculateTotalCost(dailyRate, monthlyRate);

                // Add to list
                activeLeases.add(lease);

                // Print for debugging
                //System.out.println("Found Lease: " + lease);
            }

            if (!found) {
                System.out.println("No active leases found in database.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return activeLeases;
    }


    
    public List<Lease> listLeaseHistory() {
        List<Lease> leases = new ArrayList<>();
        String query = "SELECT * FROM lease WHERE end_date < CURDATE()";  // Past leases

        try (Connection conn = DBConnUtil.getConnection(PROPERTY_FILE);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Lease lease = new Lease(
                    rs.getInt("lease_id"),
                    rs.getInt("customer_id"),
                    rs.getInt("vehicle_id"),
                    rs.getDate("start_date"),
                    rs.getDate("end_date"),
                    rs.getString("type")
                );

                // 🔹 Fetch rates (Replace with actual DB query)
                double dailyRate = 50.0;
                double monthlyRate = 1000.0;

                // 🔹 Calculate Total Cost
                lease.calculateTotalCost(dailyRate, monthlyRate);

                leases.add(lease);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return leases;
    }
    
    
    //Task 10
    @Override
    public Lease getLeaseById(int leaseId) throws DataNotFoundException {
        try (Connection conn = DBConnUtil.getConnection(PROPERTY_FILE);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Lease WHERE lease_id = ?")) {

            stmt.setInt(1, leaseId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Lease(
                    rs.getInt("lease_id"),
                    rs.getInt("vehicle_id"),
                    rs.getInt("customer_id"),
                    rs.getDate("start_date"),
                    rs.getDate("end_date"),
                    rs.getString("type")
                );
            } else {
                throw new DataNotFoundException("Lease not found");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error while retrieving lease", e); 
        }
    }





    
 // Payment Handling
    @Override
    public void makePayment(Payment payment) {
        String query = "INSERT INTO payment (lease_id, payment_date, amount) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, payment.getLeaseID());
            pstmt.setDate(2, new java.sql.Date(System.currentTimeMillis())); // Current date
            pstmt.setDouble(3, payment.getAmount());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Payment> listPayments() {
        List<Payment> payments = new ArrayList<>();
        String query = "SELECT * FROM payment";
        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                payments.add(new Payment(
                    rs.getInt("payment_id"),
                    rs.getInt("lease_id"),
                    rs.getDate("payment_date"),
                    rs.getDouble("amount")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    
    
    @Override
    public Payment findPaymentById(int paymentID) throws DataNotFoundException{
        String sql = "SELECT * FROM payment WHERE payment_id = ?"; 
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, paymentID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Payment( 
                    rs.getInt("payment_id"),
                    rs.getInt("lease_id"),
                    rs.getDate("payment_date"),
                    rs.getDouble("amount")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if payment not found
    }
    
    

    @Override
    public void recordPayment(Lease lease, double amount) {
        String query = "INSERT INTO payment (lease_id, payment_date, amount) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, lease.getLeaseID());
            pstmt.setDate(2, new java.sql.Date(System.currentTimeMillis())); // Set current date
            pstmt.setDouble(3, amount);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Utility methods for mapping ResultSet to objects

    // Map ResultSet to Vehicle object
    private Vehicle mapCar(ResultSet rs) throws SQLException {
        return new Vehicle(
                rs.getInt("vehicle_id"),
                rs.getString("make"),
                rs.getString("model"),
                rs.getInt("year"),
                rs.getDouble("daily_rate"),
                rs.getString("status"),
                rs.getInt("passenger_capacity"),
                rs.getDouble("engine_capacity"));
    }
    

    // Map ResultSet to Customer object
    private Customer mapCustomer(ResultSet rs) throws SQLException {
        return new Customer(
                rs.getInt("customer_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("phone_number"));
    }
    

    // Map ResultSet to Lease object
 // Map ResultSet to Lease object
    private Lease mapLease(ResultSet rs) throws SQLException {
        return new Lease(
            rs.getInt("lease_id"),
            rs.getInt("vehicle_id"),
            rs.getInt("customer_id"),
            rs.getDate("start_date"),
            rs.getDate("end_date"),
            rs.getString("type")
            //rs.getDouble("total_cost")
        );
    }

    

    // Map ResultSet to Payment object
    private Payment mapPayment(ResultSet rs) throws SQLException {
        return new Payment(
                rs.getInt("payment_id"),
                rs.getInt("lease_id"),
                rs.getDate("payment_date"),
                rs.getDouble("amount"));
    }


}
