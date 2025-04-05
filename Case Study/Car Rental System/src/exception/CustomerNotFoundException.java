package exception;

public class CustomerNotFoundException extends Exception{
	
	private static final long serialVersionUID = 1L;  // Add this line
	public CustomerNotFoundException(String message) {
        super(message);
    }

}
