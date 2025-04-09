package entity;

import exception.*;
import java.util.*;


//Represents payment made by the student along with amount and date
public class Payment {
    private int paymentId;
    private Student student;
    private double amount;
    private Date paymentDate;
    private List<Payment> payments;               // Store payments for the student

    // Constructor
    public Payment(int paymentId, Student student, double amount, Date paymentDate) throws PaymentValidationException {

        if (amount <= 0) {
            throw new PaymentValidationException("Payment amount must be greater than zero");
        }
        
        this.paymentId = paymentId;
        this.student = student;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.payments = new ArrayList<>();

    }



    //Task -3  Methods
    // Getter method to retrieve student information
    public Student getStudent() {
        return student;
    }

    // Getter method to retrieve payment amount
    public double getPaymentAmount() {
        return amount;
    }

    
    // Getter method to retrieve payment date
    public Date getPaymentDate() {
        return paymentDate;
    }

    // Returns payment ID
    public int getPaymentId() {
        return paymentId;
    }


    // Displays payment details
    public void displayPaymentInfo() {
        System.out.println("Payment ID: " + paymentId + ", Student: " + student.getFirstName() + ", Amount: " + amount);
    }

    // Task 6: Override toString for better debugging
    @Override
    public String toString() {
        return "Payment ID: " + paymentId + ", Student: " + student.getFirstName() +
               ", Amount: $" + amount + ", Date: " + paymentDate;
    }


    // Task 6: Method to add a payment to the student
    public void addPayment(Payment payment) {
        payments.add(payment);
    }

    // Task 6: Get list of payments
    public List<Payment> getPayments() {
        return new ArrayList<>(payments);         // Returning a copy to prevent modifications
    }
}
