package src.exception;

// Custom exception for invalid enrollment data
public class InvalidEnrollmentDataException extends Exception {
    public InvalidEnrollmentDataException(String message) {
        super(message);
    }
}