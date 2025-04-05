package exception;


public class LeaseNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public LeaseNotFoundException(String message) {
        super(message);
    }
}

