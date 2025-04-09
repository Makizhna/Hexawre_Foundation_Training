package exception;


// Custom exception when patient is not found
public class PatientNumberNotFoundException extends Exception {
    public PatientNumberNotFoundException(String message) {
        super(message);
    }
}
