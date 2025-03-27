create database sisdb;
use sisdb;


CREATE TABLE Students (
    student_id INT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    date_of_birth DATE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(15) UNIQUE NOT NULL
);


CREATE TABLE Courses (
    course_id VARCHAR(5) PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL,
    credits INT NOT NULL,
    teacher_id INT,
    FOREIGN KEY (teacher_id) REFERENCES Teacher(teacher_id) 
);


CREATE TABLE Teacher (
    teacher_id INT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);


CREATE TABLE Enrollments (
    enrollment_id VARCHAR(5) PRIMARY KEY,
    student_id INT,
    course_id VARCHAR(5),
    enrollment_date DATE NOT NULL,
    FOREIGN KEY (student_id) REFERENCES Students(student_id) ,
    FOREIGN KEY (course_id) REFERENCES Courses(course_id) 
);

CREATE TABLE Payments (
    payment_id INT PRIMARY KEY,
    student_id INT,
    amount DECIMAL(10,2) NOT NULL,
    payment_date DATE NOT NULL,
    FOREIGN KEY (student_id) REFERENCES Students(student_id) 
);


insert into Students (student_id, first_name, last_name, date_of_birth, email, phone_number) values
(110, 'Hughie', 'Campbell', '1995-08-15', 'hughie.campbell@gmail.com', '9837567890'),
(210, 'Billy', 'Butcher', '1998-06-20', 'billy.butcher@gmail.com', '7345678901'),
(310, 'Albert', 'Bosh', '2000-01-10', 'albert@gmail.com', '9456789012'),
(410, 'Meera', 'Patil', '1997-05-25', 'meera13@gmail.com', '8567890123'),
(510, 'David', 'Williams', '1999-09-30', 'david54@gmail.com', '7878901234'),
(610, 'Emma', 'Jones', '1996-12-14', 'emma.jones@gmail.com', '8789012345'),
(710, 'Franklin', 'Miller', '1994-07-07', 'franklinmiller@gmail.com', '7890123456'),
(810, 'Calvin', 'Davis', '2001-03-11', 'calvindavis@gmail.com', '8901234567'),
(910, 'Lohith', 'Aksha', '1993-11-22', 'lohithaksha12@gmail.com', '9012345678'),
(1010, 'Isaac', 'Newton', '1998-04-05', 'isaacnetwon100@gmail.com', '8123456789');


insert into Courses (course_id, course_name, credits, teacher_id) values
('C01', 'Artificial Intelligence', 4, 1),
('C02', 'Internet of Things', 3, 2),
('C03', 'Computer Science', 4, 3),
('C04', 'Cyber Security', 3, 4),
('C05', 'Data Science', 4, 5),
('C06', 'Software Engineering', 3, 6),
('C07', 'Blockchain Technology', 4, 7),
('C08', 'Cloud Computing', 3, 8),
('C09', 'Machine Learning', 4, 9),
('C10', 'Embedded Systems', 3, 10);



insert into Teacher (teacher_id, first_name, last_name, email) values
(1, 'Reachel', 'Merlin', 'reachelmerlin@gmail.com'),
(2, 'Aashwa', 'Damin', 'aashwadamin@gmail.com'),
(3, 'Moshina', 'Rahmath', 'moshina.rahmath@gmail.com'),
(4, 'Naveen', 'Gupte', 'Naveen13@gmail.com'),
(5, 'Kalai', 'Sudar', 'kalaisudar46@gmail.com'),
(6, 'Barath', 'Kumar', 'barathkumar@gmail.com'),
(7, 'Suha', 'shre', 'suha.shre@gmail.com'),
(8, 'Jana', 'Varshini', 'jana.varshini@gmail.com'),
(9, 'Felci', 'Christina', 'felci10christina@gmail.com'),
(10, 'Evangelin', 'Jenifer', 'evejeni23@gmail.com');


select * from teacher;




INSERT INTO Enrollments (enrollment_id, student_id, course_id, enrollment_date) VALUES
('E01', 110, 'C01', '2024-01-10'),
('E02', 210, 'C02', '2024-01-12'),
('E03', 310, 'C03', '2024-01-14'),
('E04', 410, 'C04', '2024-01-16'),
('E05', 510, 'C05', '2024-01-18'),
('E06', 610, 'C06', '2024-01-20'),
('E07', 710, 'C07', '2024-01-22'),
('E08', 810, 'C08', '2024-01-24'),
('E09', 910, 'C09', '2024-01-26'),
('E10', 1010 ,'C10' ,'2024-01-28');


select * from enrollments;


INSERT INTO Payments (payment_id, student_id, amount, payment_date) VALUES
(11, 110, 500.00, '2024-02-10'),
(22, 210, 600.00, '2024-02-12'),
(33, 310, 450.00, '2024-02-14'),
(44, 410, 700.00, '2024-02-16'),
(55, 510, 550.00, '2024-02-18'),
(66, 610, 650.00, '2024-02-20'),
(77, 710, 500.00, '2024-02-22'),
(88, 810, 400.00, '2024-02-24'),
(99, 910, 300.00, '2024-02-26'),
(100, 1010, 750.00, '2024-02-28');


select * from payments;

use sisdb;
insert into Students (student_id,first_name, last_name, date_of_birth, email, phone_number) values
(1011, 'Firthose', 'Banu', '1995-08-15', 'firthosebanu@example.com', '7564667890');

insert into Enrollments (enrollment_id, student_id, course_id, enrollment_date)
values ('E11', 1011, 'C01', '2024-03-01');


update Teacher
set email = 'hello.reachelmerlin@gmail.com'
WHERE teacher_id = 1;


delete from Enrollments
where student_id = 110 and course_id = 'C01';

update Courses
set teacher_id = 2
where course_id = 'C09';


delete from Payments where student_id = 210;
delete from Enrollments where student_id = 210;
delete from Students where student_id = 210;


update Payments
set amount = 750.00
where payment_id = 22;


use sisdb;

select s.student_id, s.first_name, s.last_name, sum(p.amount) as total_payment from students s
join payments p on s.student_id = p.student_id where s.student_id = 110  
group by s.student_id, s.first_name, s.last_name;


select c.course_id, c.course_name, count(e.student_id) as student_count
from courses c left join enrollments e on c.course_id = e.course_id 
group by c.course_id, c.course_name
order by student_count desc;


select s.student_id, s.first_name, s.last_name from students s
left join enrollments e on s.student_id = e.student_id
where e.student_id is null;


select s.first_name, s.last_name, c.course_name from students s
join enrollments e on s.student_id = e.student_id
join courses c on e.course_id = c.course_id;


SELECT t.first_name, t.last_name, c.course_name 
FROM Teacher t
JOIN Courses c ON t.teacher_id = c.teacher_id;



select s.first_name, s.last_name, e.enrollment_date from students s
join enrollments e on s.student_id = e.student_id
join courses c on e.course_id = c.course_id
where c.course_name = 'artificial intelligence'
order by e.enrollment_date;


select s.student_id, s.first_name, s.last_name
from students s
left join payments p on s.student_id = p.student_id
where p.student_id is null;



select c.course_id, c.course_name
from courses c
left join enrollments e on c.course_id = e.course_id
where e.course_id is null;


select e.student_id, s.first_name, s.last_name, count(e.course_id) as num_of_courses
from enrollments e
join students s on e.student_id = s.student_id
group by e.student_id, s.first_name, s.last_name
having num_of_courses > 1;


select t.teacher_id, t.first_name, t.last_name
from teacher t
left join courses c on t.teacher_id = c.teacher_id
where c.teacher_id is null;








