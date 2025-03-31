import java.util.ArrayList;
import java.util.List;


//Represents a Teacher with personal details and assigned courses
public class Teacher {
    private int teacherId;
    private String firstName;
    private String lastName;
    private String email;
    private List<Course> assignedCourses;  //List of courses assigned to the teacher


    // Constructor to initialize teacher details
    public Teacher(int teacherId, String firstName, String lastName, String email) throws InvalidTeacherDataException{
        
        //Ensures that the email format is valid before assigning values.
        if (email == null || !email.contains("@")) {
            throw new InvalidTeacherDataException("Invalid email format");
        }

        this.teacherId = teacherId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.assignedCourses = new ArrayList<>();
    }


    //Methods

    //Updates teacher information such as name and email.
    //Ensures first name and last name are not empty before updating.
    public void updateTeacherInfo(String firstName, String lastName, String email) throws InvalidTeacherDataException {

        if (firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty()) {
            throw new InvalidTeacherDataException("First name and last name cannot be empty.");
        }
        
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        System.out.println("Teacher information updated.");
    }

    //Displays basic teacher information such as ID and full name
    public void displayTeacherInfo() {
        System.out.println("Teacher ID: " + teacherId + ", Name: " + firstName + " " + lastName);
    }

    //returns the list of courses assigned to the teacher.
    public List<Course> getAssignedCourses() {
        return assignedCourses;
    }

    //returns First name of the teacher
    public String getFirstName() {
        return firstName;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    //return Teacher's ID
    public int getTeacherId() { 
        return teacherId; 
    }

    //return lastname of the teacher
    public String getLastName() { 
        return lastName; 
    }

    //return Email address of the teacher
    public String getEmail() { 
        return email; 
    }
    
}

