
use sisdb;

insert into Students (student_id,first_name, last_name, date_of_birth, email, phone_number) values
(1011, 'Firthose', 'Banu', '1995-08-15', 'firthosebanu@example.com', '7564667890');

insert into Enrollments (enrollment_id, student_id, course_id, enrollment_date)
values ('E11', 1011, 'C01', '2024-03-01');


update Teacher set email = 'hello.reachelmerlin@gmail.com'
where teacher_id = 1;


DELETE FROM Enrollments
WHERE student_id = 110 AND course_id = 'C01';

update Courses set teacher_id = 2
where course_id = 'C09';


delete from Payments where student_id = 210;
delete from Enrollments where student_id = 210;
delete from Students where student_id = 210;


update Payments set amount = 750.00
where payment_id = 22;



