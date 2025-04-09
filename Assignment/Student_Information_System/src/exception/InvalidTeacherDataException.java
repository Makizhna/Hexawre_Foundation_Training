package exception;


//Custom exception for invalid teacher data

public class InvalidTeacherDataException extends Exception {
    public InvalidTeacherDataException(String message) {
        super(message);
    }
}

