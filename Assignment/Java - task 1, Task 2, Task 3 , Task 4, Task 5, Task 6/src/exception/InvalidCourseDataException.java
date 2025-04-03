package src.exception;


// Custom exception for invalid course data
public class InvalidCourseDataException extends Exception {
    public InvalidCourseDataException(String message) {
        super(message);
    }
}
