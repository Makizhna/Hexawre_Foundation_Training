-- Java Coding Challenge
-- database creation
create database hospitaldb;
use hospitaldb;

-- 1. patient table
create table  patient (
    patientId int primary key auto_increment,
    firstName varchar(50) not null,
    lastName varchar(50) not null,
    dateOfBirth date not null,
    gender enum('male', 'female', 'other') not null,
    contactNumber varchar(15) not null,
    address varchar(255)
);

-- 2. doctor table
create table  doctor (
    doctorId int primary key auto_increment,
    firstName varchar(50) not null,
    lastName varchar(50) not null,
    specialization varchar(100),
    contactNumber varchar(15) not null
);

-- 3. appointment table
create table  appointment (
    appointmentId int primary key auto_increment,
    patientId int not null,
    doctorId int not null,
    appointmentDate datetime not null,
    description text,
    foreign key (patientid) references patient(patientid) on delete cascade,
    foreign key (doctorid) references doctor(doctorid) on delete cascade
);


-- Insert Patients
insert into patient (firstName, lastName, dateOfBirth, gender, contactNumber, address) values
('Arun', 'Kumar', '1990-05-12', 'male', '9876543210', 'Chennai'),
('Meera', 'Sundar', '1985-08-23', 'female', '9876543211', 'Madurai'),
('Ravi', 'Sharma', '2000-01-15', 'male', '9876543212', 'Coimbatore'),
('Priya', 'Raj', '1993-11-20', 'female', '9876543213', 'Salem'),
('Ashwin', 'Kumar', '1978-07-02', 'male', '9876543214', 'Trichy'),
('Anita', 'Joshi', '1988-04-18', 'female', '9876543215', 'Erode'),
('Vinod', 'Das', '1995-09-30', 'male', '9876543216', 'Tirunelveli');

--  Insert Doctors 
insert into doctor (firstName, lastName, specialization, contactNumber) values
('Suresh', 'Babu', 'Cardiologist', '9000000001'),
('Lakshmi', 'Menon', 'Dermatologist', '9000000002'),
('Ramesh', 'Kumar', 'Pediatrician', '9000000003'),
('Divya', 'Nair', 'Neurologist', '9000000004'),
('Ganesh', 'Mohan', 'Orthopedic', '9000000005'),
('Anjali', 'Iyer', 'ENT', '9000000006'),
('Vikram', 'Patel', 'General Medicine', '9000000007');

-- Insert Appointments 
insert into appointment (patientId, doctorId, appointmentDate, description) values
(1, 1, '2025-04-10 09:30:00', 'Regular heart check-up'),
(2, 2, '2025-04-11 10:00:00', 'Skin rash consultation'),
(3, 3, '2025-04-12 11:00:00', 'Child vaccination'),
(4, 4, '2025-04-13 14:30:00', 'Migraine follow-up'),
(5, 5, '2025-04-14 15:00:00', 'Knee pain consultation'),
(6, 6, '2025-04-15 09:00:00', 'Ear infection'),
(7, 7, '2025-04-16 10:30:00', 'General health check-up');