
use sisdb;
INSERT INTO Students (student_id,first_name, last_name, date_of_birth, email, phone_number) VALUES
(1011, 'Firthose', 'Banu', '1995-08-15', 'firthosebanu@example.com', '7564667890');


INSERT INTO Enrollments (enrollment_id, student_id, course_id, enrollment_date)
VALUES ('E11', 1011, 'C01', '2024-03-01');


UPDATE Teacher
SET email = 'hello.reachelmerlin@gmail.com'
WHERE teacher_id = 1;


DELETE FROM Enrollments
WHERE student_id = 110 AND course_id = 'C01';

UPDATE Courses
SET teacher_id = 2
WHERE course_id = 'C09';


DELETE FROM Payments WHERE student_id = 210;
DELETE FROM Enrollments
WHERE student_id = 210;
DELETE FROM Students
WHERE student_id = 210;


UPDATE Payments
SET amount = 750.00
WHERE payment_id = 22;

