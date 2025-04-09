package exception;


//Custom exception for teacher not found
public class TeacherNotFoundException extends Exception{
 public TeacherNotFoundException(String message) {
     super(message);
 }   
}