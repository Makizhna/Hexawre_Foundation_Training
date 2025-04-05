package entity;

import java.sql.Date;
import java.time.temporal.ChronoUnit;

public class Lease {
    private int lease_id;
    private int vehicle_id;
    private int customer_id;
    private Date start_date;
    private Date end_date;
    private String lease_type; // Daily or Monthly
    private double total_cost;

    // Default constructor
    public Lease() {}

    // Parameterized constructor
    public Lease(int lease_id, int vehicle_id, int customer_id, Date start_date, Date end_date, String lease_type, double total_cost) {
        this.lease_id = lease_id;
        this.vehicle_id = vehicle_id;
        this.customer_id = customer_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.lease_type = lease_type;
        this.total_cost = total_cost;
    }

    
    public Lease(int lease_id, int vehicle_id, int customer_id, Date start_date, Date end_date, String lease_type) {
        this.lease_id = lease_id;
        this.vehicle_id = vehicle_id;
        this.customer_id = customer_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.lease_type = lease_type;
        this.total_cost = 0.0;               // Default value
    }
    
    
    // Getters
    public int getLeaseID() { 
    	return lease_id; 
    }
    
    public int getVehicleID() { 
    	return vehicle_id; 
    }
    
    public int getCustomerID() { 
    	return customer_id; 
    }
    
    public Date getStartDate() { 
    	return start_date; 
    }
    
    public Date getEndDate() { 
    	return end_date; 
    }
    
    public String getLeaseType() { 
    	return lease_type; 
    }
    
    public double getTotalCost() { 
    	return total_cost; 
    }

    // Setters
    public void setLeaseID(int lease_id) { 
    	this.lease_id = lease_id; 
    }
    
    public void setVehicleID(int vehicle_id) { 
    	this.vehicle_id = vehicle_id; 
    }
    
    public void setCustomerID(int customer_id) { 
    	this.customer_id = customer_id; 
    }
    
    public void setStartDate(Date start_date) { 
    	this.start_date = start_date; 
    }
    
    public void setEndDate(Date end_date) { 
    	this.end_date = end_date; 
    }
    
    public void setLeaseType(String lease_type) { 
    	this.lease_type = lease_type; 
    }
    
    public void setTotalCost(double total_cost) { 
    	this.total_cost = total_cost; 
    }
    
    
 // Method to calculate total cost
    public void calculateTotalCost(double dailyRate, double monthlyRate) {
        if (start_date == null || end_date == null) {
            System.out.println("Error: Start date or End date is NULL!");
            return;
        }

        long days = ChronoUnit.DAYS.between(start_date.toLocalDate(), end_date.toLocalDate());
        System.out.println("Days between: " + days); // Debugging

        if (lease_type.equalsIgnoreCase("daily_lease")) {
            total_cost = days * dailyRate;
        } else if (lease_type.equalsIgnoreCase("monthly_lease")) {
            long months = days / 30;
            total_cost = months * monthlyRate;
        }

        System.out.println("Total Cost Calculated: $" + total_cost); // Debugging
    }

    
    @Override
    public String toString() {
        return "--------------------------------\n" +
               "Lease ID     : " + lease_id + "\n" +
               "Customer ID  : " + customer_id + "\n" +
               "Vehicle ID   : " + vehicle_id + "\n" +
               "Start Date   : " + start_date + "\n" +
               "End Date     : " + end_date + "\n" +
               "Lease Type   : " + lease_type + "\n" +
               "Total Cost   : $" + total_cost + "\n" +
               "--------------------------------";
    }
}
