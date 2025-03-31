
import java.util.*;


//Represents payment made by the student along with amount and date
public class Payment {
    private int paymentId;
    private Student student;
    private double amount;
    private Date paymentDate;

    // Constructor
    public Payment(int paymentId, Student student, double amount, Date paymentDate) throws PaymentValidationException {

        if (amount <= 0) {
            throw new PaymentValidationException("Payment amount must be greater than zero");
        }
        
        this.paymentId = paymentId;
        this.student = student;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    // Methods
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


    // Displays payment details
    public void displayPaymentInfo() {
        System.out.println("Payment ID: " + paymentId + ", Student: " + student.getFirstName() + ", Amount: " + amount);
    }
}
