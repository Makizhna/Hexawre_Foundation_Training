package exception;

// Custom exception for invalid payment
public class PaymentValidationException extends Exception {
    public PaymentValidationException(String message) {
        super(message);
    }
}

