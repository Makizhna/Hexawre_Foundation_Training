SELECT * FROM sisdb.students;

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



use sisdb;

select avg(student_count) as avg_students_per_course
from (
    select course_id, count(student_id) as student_count
    from enrollments group by course_id) as course_enrollments;


Select first_name, last_name from Students 
where student_id = (
    select student_id from Payments 
    where amount = (select MAX(amount) from Payments)
);


select c.course_id, c.course_name, count(e.student_id) as enrollment_count from courses c
join enrollments e on c.course_id = e.course_id
group by c.course_id, c.course_name
having count(e.student_id) = (
    select max(student_count)
    from (select count(student_id) as student_count
        from enrollments
        group by course_id) as max_enrollments
);


select t.teacher_id, t.first_name, t.last_name, 
       (select sum(p.amount) from payments p
        join enrollments e on p.student_id = e.student_id
        join courses c on e.course_id = c.course_id
        where c.teacher_id = t.teacher_id) as total_payments
from teacher t;


select s.student_id, s.first_name, s.last_name
from students s
where (select count(distinct course_id) from enrollments where student_id = s.student_id) = (select count(*) from courses);



select teacher_id, first_name, last_name from teacher
where teacher_id not in (select distinct teacher_id from courses);


select avg(age) as average_age 
from (select datediff(curdate(), date_of_birth) / 365 as age 
from students) as age_subquery;



select course_id, course_name from courses
where course_id not in (select distinct course_id from enrollments);



select s.student_id, s.first_name, s.last_name, c.course_name, (select sum(p.amount) 
from payments p where p.student_id = s.student_id) as total_payment
from students s
join enrollments e on s.student_id = e.student_id
join courses c on e.course_id = c.course_id;


select student_id, first_name, last_name
from students
where student_id in (
    select student_id 
    from payments 
    group by student_id
    having count(payment_id) > 1
);


select s.student_id, s.first_name, s.last_name, sum(p.amount) as total_payments
from students s
join payments p on s.student_id = p.student_id
group by s.student_id;



select c.course_name, count(e.student_id) as student_count
from courses c
left join enrollments e on c.course_id = e.course_id
group by c.course_name;




select s.student_id, s.first_name, s.last_name, avg(p.amount) as avg_payment
from students s
join payments p on s.student_id = p.student_id
group by s.student_id;










/*Case study*/
create database carrentalsystem;
use carrentalsystem;


create table vehicle (
    vehicle_id int auto_increment primary key,
    make varchar(50) not null,
    model varchar(50) not null,
    year int not null,
    daily_rate decimal(10,2) not null,
    status varchar(20) not null,  
    passenger_capacity int not null,
    engine_capacity decimal(5,2) not null
);


create table customer (
    customer_id int primary key,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    email varchar(100) unique not null,
    phone_number varchar(15) unique not null
);


create table lease (
    lease_id int primary key,
    vehicle_id int not null,
    customer_id int not null,
    start_date date not null,
    end_date date not null,
    type varchar(20) not null, 
    foreign key (vehicle_id) references vehicle(vehicle_id),
    foreign key (customer_id) references customer(customer_id)
);


create table payment (
    payment_id int primary key,
    lease_id int not null,
    payment_date date not null,
    amount decimal(10,2) not null,
    foreign key (lease_id) references lease(lease_id)
);



insert into vehicle (make, model, year, daily_rate, status, passenger_capacity, engine_capacity) values
('Maruti', 'Swift', 2018, 35.00, 'available', 5, 1.2),
('Hyundai', 'i20', 2020, 40.00, 'not_available', 5, 1.4),
('Kia', 'Sonet', 2020, 48.00, 'available', 5, 1.0),
('Mahindra', 'XUV700', 2023, 95.00, 'available', 7, 2.2),
('Audi', 'A4', 2021, 85.00, 'not_available', 5, 2.0),
('Renault', 'Kiger', 2021, 42.00, 'available', 5, 1.0),
('BMW', 'X5', 2022, 100.00, 'available', 7, 3.0),
('Ford', 'Focus', 2021, 55.00, 'not_available', 5, 2.0),
('Toyota', 'Fortuner', 2022, 90.00, 'not_available', 7, 2.8),
('Tata', 'Nexon', 2019, 50.00, 'available', 5, 1.5);

select * from vehicle;

insert into customer (customer_id, first_name, last_name, email, phone_number) values
(111,'Meera', 'Jasmine', 'meera_jas@gmail.com', '8774567890'),
(211,'Amaina', 'Banu', 'aamina.banu@gmail.com', '6787654321'),
(311,'Rakshana', 'Shre', 'rakshana@gmail.com', '5678978908'),
(411,'Reethiga', 'Shre', 'reethi123@gmail.com', '8657838299'),
(511,'Senthil', 'Velan', 'senthil$345@gmail.com', '98654364876'),
(611,'Ram', 'kumar', 'ramkumar@gmail.com', '8674845923'),
(711,'Siva', 'Ranjani', 'sivaranjani@gmail.com', '8895467835'),
(811,'Moshina', 'Rahmath', 'moshina24@gmail.com', '8957428673'),
(911,'Indhu', 'Mathi', 'indhumathi@gmail.com', '7343267658'),
(1011,'Syed', 'Anwar', 'syedanwar@gmail.com', '8899934643');


select * from customer;

insert into lease (lease_id, vehicle_id, customer_id, start_date, end_date, type) values
(1001, 1, 111, '2024-01-10', '2024-01-20', 'daily_lease'),
(1002, 2, 211, '2024-02-05', '2024-03-05', 'daily_lease'),
(1003, 3, 311, '2024-03-01', '2024-03-10', 'monthly_lease'),
(1004, 4, 411, '2024-03-15', '2024-04-15', 'monthly_lease'),
(1005, 5, 511, '2024-04-01', '2024-04-07', 'daily_lease'),
(1006, 6, 611, '2024-04-10', '2024-05-10', 'monthly_lease'),
(1007, 7, 711, '2024-05-01', '2024-05-15', 'daily_lease'),
(1008, 8, 811, '2024-05-20', '2024-06-20', 'monthly_lease'),
(1009, 9, 911, '2024-06-01', '2024-06-10', 'monthly_lease'),
(1010, 10, 1011, '2024-06-15', '2024-07-15', 'daily_lease');


select * from lease;

insert into payment (payment_id, lease_id, payment_date, amount) values
(201, 1001, '2024-01-20', 3500.00),
(202, 1002, '2024-03-05', 12000.00),
(203, 1003, '2024-03-10', 4800.00),
(204, 1004, '2024-04-15', 15000.00),
(205, 1005, '2024-04-07', 4200.00),
(206, 1006, '2024-05-10', 13500.00),
(207, 1007, '2024-05-15', 6500.00),
(208, 1008, '2024-06-20', 16000.00),
(209, 1009, '2024-06-10', 4800.00),
(210, 1010, '2024-07-15', 18000.00);


select * from payment;