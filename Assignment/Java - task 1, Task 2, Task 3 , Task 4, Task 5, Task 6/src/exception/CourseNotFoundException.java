package src.exception;

// Custom exception for course not found
public class CourseNotFoundException extends Exception{
    public CourseNotFoundException(String message) {
        super(message);
    }   
}
