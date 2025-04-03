package src.exception;

// Custom exception for handling insufficient funds in payments
public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
