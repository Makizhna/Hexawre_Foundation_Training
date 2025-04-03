package src.exception;


// Custom exception for invalid student data
public class InvalidStudentDataException extends Exception {
    public InvalidStudentDataException(String message) {
        super(message);
    }
}
